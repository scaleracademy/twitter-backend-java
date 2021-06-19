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

import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    if (userNameOrUserId.startsWith("@")) {
      var username = userNameOrUserId.substring(1);
      userResponse = new UserResponse(userService.getUserByUserName(username));
      return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    var userId = utility.converStringToUUID(userNameOrUserId);
    userResponse = new UserResponse(userService.getUserByUserId(userId));
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  @PostMapping
  ResponseEntity<UserResponse> createUser(@RequestBody UserResponse userResponse) {
    UserResponse createdUser = new UserResponse(userService.addUser(userResponse));
    return new ResponseEntity<>(createdUser, HttpStatus.OK);
  }

  @PatchMapping
  ResponseEntity<UserResponse> updateUser(@RequestBody UserResponse userResponse) {
    UserResponse updatedUser = new UserResponse(userService.editUser(userResponse));
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }

  @PutMapping("/{userId}/follow")
  ResponseEntity<HttpStatus> addFollower(@PathVariable UUID userId) {
    userService.addFollower(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{userId}/follow")
  ResponseEntity<HttpStatus> removeFollower(@PathVariable("userId") UUID userId) {
    userService.removeFollower(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{userId}/followers")
  ResponseEntity<List<UserResponse>> getFollowers(@PathVariable("userId") UUID userId) {
    List<UserResponse> followers = userService.getFollowers(userId);
    return new ResponseEntity<>(followers, HttpStatus.OK);
  }

  @GetMapping("/{userId}/followings")
  ResponseEntity<List<UserResponse>> getFollowings(@PathVariable("userId") UUID userId) {
    List<UserResponse> followings = userService.getFollowings(userId);
    return new ResponseEntity<>(followings, HttpStatus.OK);
  }
}
