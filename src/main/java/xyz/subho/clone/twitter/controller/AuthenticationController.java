/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2026 Subhrodip Mohanta (hello@subho.xyz)
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

package xyz.subho.clone.twitter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.subho.clone.twitter.constant.AuthV1Constants;
import xyz.subho.clone.twitter.exception.BadRequestException;
import xyz.subho.clone.twitter.model.AuthenticationRequest;
import xyz.subho.clone.twitter.model.AuthenticationResponse;
import xyz.subho.clone.twitter.security.JwtUtil;
import xyz.subho.clone.twitter.security.UserDetailsServiceImpl;

@RestController
@RequestMapping(AuthV1Constants.BASE_PATH)
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtTokenUtil;
  private final UserDetailsServiceImpl userDetailsService;

  public AuthenticationController(
      AuthenticationManager authenticationManager,
      JwtUtil jwtTokenUtil,
      UserDetailsServiceImpl userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenUtil = jwtTokenUtil;
    this.userDetailsService = userDetailsService;
  }

  @PostMapping(AuthV1Constants.AUTHENTICATE)
  public ResponseEntity<?> createAuthenticationToken(
      @RequestBody AuthenticationRequest authenticationRequest) {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              authenticationRequest.username(), authenticationRequest.password()));
    } catch (BadCredentialsException e) {
      throw new BadRequestException("Incorrect username or password", e);
    }

    final var userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username());

    final String jwt = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }
}
