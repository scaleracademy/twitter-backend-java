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

package xyz.subho.clone.twitter.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.subho.clone.twitter.model.UserResponse;
import xyz.subho.clone.twitter.service.UserService;
import xyz.subho.clone.twitter.utility.Utility;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

  // @Autowired
  private UserService userService;

  @Autowired private Utility utility;

  @GetMapping("/{userNameOrUserId}")
  ResponseEntity<UserResponse> getUserByUserIdOrUserName(
      @PathVariable("userNameOrUserId") String userNameOrUserId) {

    UserResponse userResponse;

    if (utility.isUUID(userNameOrUserId)) {
      var uuid = utility.converStringToUUID(userNameOrUserId);
      userResponse = new UserResponse(userService.getUserByUserId(uuid));
      return new ResponseEntity<>(userResponse, HttpStatus.OK);
    } else {
      var username =
          userNameOrUserId.startsWith("@") ? userNameOrUserId.substring(1) : userNameOrUserId;
      userResponse = new UserResponse(userService.getUserByUserName(username));
      return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
  }

  // ResponseEntity<Boolean> makeNewUser	()

}
