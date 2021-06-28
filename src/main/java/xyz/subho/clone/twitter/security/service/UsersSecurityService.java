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

package xyz.subho.clone.twitter.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.subho.clone.twitter.exception.ResourceNotFoundException;
import xyz.subho.clone.twitter.repository.UsersAuthenticationRepository;

@Service
@Slf4j
public class UsersSecurityService implements UserDetailsService {

  @Autowired private UsersAuthenticationRepository usersAuthenticationRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    var authenticatedUser = usersAuthenticationRepository.findByUsername(username);

    if (authenticatedUser == null) {
      log.warn("Username {} NOT FOUND!", username);
      throw new ResourceNotFoundException("Username: " + username + "NOT FOUND!");
    }

    return authenticatedUser;
  }
}
