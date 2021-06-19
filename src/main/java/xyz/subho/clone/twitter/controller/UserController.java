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
import xyz.subho.clone.twitter.model.UserModel;
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
  public ResponseEntity<UserModel> getUserByUserIdOrUserName(
      @PathVariable("userNameOrUserId") String userNameOrUserId) {

    UserModel userResponse;

    if (userNameOrUserId.startsWith("@")) {
      var username = userNameOrUserId.substring(1);
      userResponse = userService.getUserByUserName(username);
      return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
    var userId = utility.converStringToUUID(userNameOrUserId);
    userResponse = userService.getUserByUserId(userId);
    return new ResponseEntity<>(userResponse, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserModel> createUser(@RequestBody UserModel userResponse) {
    UserModel createdUser = userService.addUser(userResponse);
    return new ResponseEntity<>(createdUser, HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<UserModel> updateUser(@RequestBody UserModel userResponse) {
    UserModel updatedUser = userService.editUser(userResponse);
    return new ResponseEntity<>(updatedUser, HttpStatus.OK);
  }

  @PutMapping("/{userId}/follow")
  public ResponseEntity<HttpStatus> addFollower(@PathVariable UUID userId) {
    userService.addFollower(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{userId}/follow")
  public ResponseEntity<HttpStatus> removeFollower(@PathVariable("userId") UUID userId) {
    userService.removeFollower(userId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/{userId}/followers")
  public ResponseEntity<List<UserModel>> getFollowers(@PathVariable("userId") UUID userId) {
    List<UserModel> followers = userService.getFollowers(userId);
    return new ResponseEntity<>(followers, HttpStatus.OK);
  }

  @GetMapping("/{userId}/followings")
  public ResponseEntity<List<UserModel>> getFollowings(@PathVariable("userId") UUID userId) {
    List<UserModel> followings = userService.getFollowings(userId);
    return new ResponseEntity<>(followings, HttpStatus.OK);
  }
}
