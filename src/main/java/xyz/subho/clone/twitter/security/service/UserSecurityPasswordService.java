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

import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import xyz.subho.clone.twitter.exception.ResourceNotFoundException;
import xyz.subho.clone.twitter.repository.UsersAuthenticationRepository;
import xyz.subho.clone.twitter.security.entity.UsersAuthenticationDetails;

@Service
@Slf4j
public class UserSecurityPasswordService implements UserDetailsPasswordService {

  @Autowired private UsersAuthenticationRepository usersAuthenticationRepository;

  @Override
  @Transactional
  public UserDetails updatePassword(UserDetails userDetails, String newPassword) {

    var optionalUser = usersAuthenticationRepository.findTopByUsername(userDetails.getUsername());

    if (optionalUser.isPresent()) {
      UsersAuthenticationDetails user = optionalUser.get();
      user.setPassword(newPassword);
      log.info("PASSWORD UPDATED for 'username': {}", user.getUsername());
      return user;
    }
    log.warn("Username NOT PRESENT for a PASSWORD UPDATE");
    throw new ResourceNotFoundException("No Username FOUND for PASSWORD UPDATE");
  }
}
