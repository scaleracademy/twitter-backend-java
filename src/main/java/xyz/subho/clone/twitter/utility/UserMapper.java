/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2023 Subhrodip Mohanta (hello@subho.xyz)
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

package xyz.subho.clone.twitter.utility;

import org.springframework.stereotype.Component;
import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.model.UserModel;

@Component("UserMapper")
public class UserMapper implements Mapper<Users, UserModel> {

  @Override
  public UserModel transform(Users user) {
    return new UserModel(
        user.getId(),
        user.getUsername(),
        user.getName(),
        null, // password should not be exposed
        user.getEmail(),
        user.getAvatar(),
        user.getBio(),
        user.getFollowerCount(),
        user.getFollowingCount(),
        user.getVerified());
  }

  @Override
  public Users transformBack(UserModel userModel) {
    var user = new Users();
    user.setId(userModel.id());
    user.setUsername(userModel.username());
    user.setName(userModel.name());
    user.setEmail(userModel.email());
    user.setPassword(userModel.password());
    user.setAvatar(userModel.avatar());
    user.setBio(userModel.bio());
    user.setFollowerCount(userModel.followerCount() != null ? userModel.followerCount() : 0L);
    user.setFollowingCount(userModel.followingCount() != null ? userModel.followingCount() : 0L);
    user.setVerified(userModel.verified() != null ? userModel.verified() : false);
    return user;
  }
}
