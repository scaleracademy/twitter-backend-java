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

package xyz.subho.clone.twitter.service.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.subho.clone.twitter.entity.RefreshToken;
import xyz.subho.clone.twitter.exception.BadRequestException;
import xyz.subho.clone.twitter.repository.RefreshTokenRepository;
import xyz.subho.clone.twitter.repository.UsersRepository;
import xyz.subho.clone.twitter.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

  @Value("${jwt.refreshExpiration}")
  private Long refreshTokenDurationMs;

  private final RefreshTokenRepository refreshTokenRepository;
  private final UsersRepository usersRepository;

  public RefreshTokenServiceImpl(
      RefreshTokenRepository refreshTokenRepository, UsersRepository usersRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.usersRepository = usersRepository;
  }

  @Override
  @Transactional
  public RefreshToken createRefreshToken(@NonNull UUID userId) {
    var user = usersRepository.getById(userId);

    // Clean up existing tokens for the user
    refreshTokenRepository.deleteByUsers(user);

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setUsers(user);
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    return refreshTokenRepository.save(refreshToken);
  }

  @Override
  public Optional<RefreshToken> findByToken(@NonNull String token) {
    return refreshTokenRepository.findByToken(token);
  }

  @Override
  public RefreshToken verifyExpiration(@NonNull RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new BadRequestException("Refresh token was expired. Please make a new signin request");
    }
    return token;
  }

  @Override
  @Transactional
  public int deleteByUserId(@NonNull UUID userId) {
    return refreshTokenRepository.deleteByUsers(usersRepository.getById(userId));
  }
}
