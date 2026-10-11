/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2026 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package xyz.subho.clone.twitter.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

class JwtSecurityTest {

  private final UserDetails user =
      User.withUsername("alice").password("hash").roles("USER").build();

  @AfterEach
  void clearSecurityContext() {
    SecurityContextHolder.clearContext();
  }

  @Test
  void jwtRoundTripValidatesSubjectAndExpiry() {
    JwtUtil jwtUtil = jwtUtil(60_000L);
    String token = jwtUtil.generateToken(user);

    assertThat(jwtUtil.extractUsername(token)).isEqualTo("alice");
    assertThat(jwtUtil.extractExpiration(token)).isAfter(new Date());
    assertThat(jwtUtil.validateToken(token, user)).isTrue();
    assertThat(jwtUtil.validateToken(token, User.withUsername("bob").password("hash").build()))
        .isFalse();
  }

  @Test
  void expiredAndMalformedTokensAreRejected() {
    JwtUtil expiredUtil = jwtUtil(-1L);
    String expired = expiredUtil.generateToken(user);
    assertThat(expiredUtil.validateToken(expired, user)).isFalse();

    JwtUtil jwtUtil = jwtUtil(60_000L);
    assertThrows(JwtException.class, () -> jwtUtil.extractUsername("not-a-jwt"));
    assertThat(jwtUtil.validateToken("not-a-jwt", user)).isFalse();

    JwtUtil expiredSpy = spy(jwtUtil(60_000L));
    doReturn("alice").when(expiredSpy).extractUsername("expired-without-parser-error");
    doReturn(new Date(0)).when(expiredSpy).extractExpiration("expired-without-parser-error");
    assertThat(expiredSpy.validateToken("expired-without-parser-error", user)).isFalse();
  }

  @Test
  void filterIgnoresRequestsWithoutBearerTokens() throws Exception {
    UserDetailsServiceImpl users = mock(UserDetailsServiceImpl.class);
    JwtUtil jwtUtil = mock(JwtUtil.class);
    FilterChain chain = mock(FilterChain.class);
    JwtRequestFilter filter = new JwtRequestFilter(users, jwtUtil);
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, chain);

    request.addHeader("Authorization", "Basic credentials");
    filter.doFilter(request, response, chain);

    verify(chain, times(2)).doFilter(request, response);
  }

  @Test
  void filterClearsContextForMalformedBearerTokens() throws Exception {
    UserDetailsServiceImpl users = mock(UserDetailsServiceImpl.class);
    JwtUtil jwtUtil = mock(JwtUtil.class);
    FilterChain chain = mock(FilterChain.class);
    when(jwtUtil.extractUsername("bad")).thenThrow(new JwtException("bad token"));
    JwtRequestFilter filter = new JwtRequestFilter(users, jwtUtil);
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer bad");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, chain);

    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    verify(chain).doFilter(request, response);
  }

  @Test
  void filterAuthenticatesValidBearerTokens() throws Exception {
    UserDetailsServiceImpl users = mock(UserDetailsServiceImpl.class);
    JwtUtil jwtUtil = mock(JwtUtil.class);
    FilterChain chain = mock(FilterChain.class);
    when(jwtUtil.extractUsername("good")).thenReturn("alice");
    when(users.loadUserByUsername("alice")).thenReturn(user);
    when(jwtUtil.validateToken("good", user)).thenReturn(true);
    JwtRequestFilter filter = new JwtRequestFilter(users, jwtUtil);
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer good");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, chain);

    assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("alice");
    verify(chain).doFilter(request, response);
  }

  @Test
  void filterLeavesRequestUnauthenticatedWhenTokenIsInvalidOrAlreadyAuthenticated()
      throws Exception {
    UserDetailsServiceImpl users = mock(UserDetailsServiceImpl.class);
    JwtUtil jwtUtil = mock(JwtUtil.class);
    FilterChain chain = mock(FilterChain.class);
    when(jwtUtil.extractUsername("invalid")).thenReturn("alice");
    when(users.loadUserByUsername("alice")).thenReturn(user);
    when(jwtUtil.validateToken("invalid", user)).thenReturn(false);
    JwtRequestFilter filter = new JwtRequestFilter(users, jwtUtil);
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader("Authorization", "Bearer invalid");
    MockHttpServletResponse response = new MockHttpServletResponse();

    filter.doFilter(request, response, chain);
    assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();

    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    filter.doFilter(request, response, chain);
    verify(chain, times(2)).doFilter(request, response);
  }

  private JwtUtil jwtUtil(long expiration) {
    JwtUtil jwtUtil = new JwtUtil();
    ReflectionTestUtils.setField(
        jwtUtil, "secret", "test-only-secret-that-is-at-least-32-characters-long");
    ReflectionTestUtils.setField(jwtUtil, "expiration", expiration);
    return jwtUtil;
  }
}
