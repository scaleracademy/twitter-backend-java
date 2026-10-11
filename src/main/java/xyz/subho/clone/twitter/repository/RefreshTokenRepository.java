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

package xyz.subho.clone.twitter.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.subho.clone.twitter.entity.RefreshToken;
import xyz.subho.clone.twitter.entity.Users;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

  @Query("select rt from RefreshToken rt where rt.token = :hashedToken or rt.token = :rawToken")
  Optional<RefreshToken> findByToken(
      @Param("hashedToken") String hashedToken, @Param("rawToken") String rawToken);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query(
      "select rt from RefreshToken rt join fetch rt.users "
          + "where rt.token = :hashedToken or rt.token = :rawToken")
  Optional<RefreshToken> findByTokenForUpdate(
      @Param("hashedToken") String hashedToken, @Param("rawToken") String rawToken);

  @Modifying
  int deleteByUsers(Users user);
}
