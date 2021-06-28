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

package xyz.subho.clone.twitter.utility.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PasswordUtils {

  @Autowired private final BCryptPasswordEncoder passwordEncoder;

  public Pair<String, String> createPassword(String password) {
    final var salt = HashUtils.generateSalt();
    final var saltedPassword = createPassword(password, salt);
    return Pair.of(salt, saltedPassword);
  }

  public boolean matchPassword(String presentedPassword, String salt, String matchPassword) {
    final var saltedPassword = salt + presentedPassword;
    return passwordEncoder.matches(saltedPassword, matchPassword);
  }

  private String createPassword(String password, String salt) {
    return passwordEncoder.encode(password + salt);
  }
}
