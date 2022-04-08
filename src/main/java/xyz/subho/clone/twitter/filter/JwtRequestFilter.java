/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.subho.clone.twitter.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.subho.clone.twitter.security.service.UsersSecurityService;
import xyz.subho.clone.twitter.utility.security.JwtUtil;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

  private static final String AUTHORIZATION_BEARER = "Bearer ";

  @Autowired private UsersSecurityService usersSecurityService;

  @Autowired private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String authrizationHeader = request.getHeader("Authorization");
    log.debug("AUthorization HEADER: {}", authrizationHeader);

    String username = null;
    String jwt = null;

    if (authrizationHeader != null && authrizationHeader.startsWith(AUTHORIZATION_BEARER)) {
      log.debug("JWT Authorization STARTS");
      jwt = authrizationHeader.substring(AUTHORIZATION_BEARER.length());
      log.debug("'JWT': {}", jwt);
      username = jwtUtil.extractUsername(jwt);
      log.debug("JWT 'username': {}", username);
    } else {
      log.warn("Authorization HEADER or 'Bearer ' NOT PRESENT");
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      var currentUserDetails = usersSecurityService.loadUserByUsername(username);

      if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt, currentUserDetails))) {

        var authenticationToken =
            new UsernamePasswordAuthenticationToken(
                currentUserDetails, null, currentUserDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("AUTHORIZATION SUCCESS for 'username': {}", username);
      } else {
        log.warn("INVALID JWT TOKEN: {} for 'username': {}", jwt, username);
      }
    } else {
      log.debug("CANNOT perform AUTHORIZATION as 'username' NOT PRESENT");
    }

    filterChain.doFilter(request, response);
  }
}
