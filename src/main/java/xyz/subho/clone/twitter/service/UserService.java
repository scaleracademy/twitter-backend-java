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

package xyz.subho.clone.twitter.service;

import java.util.List;
import java.util.UUID;
import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.model.UserModel;

public interface UserService {

  public UserModel getUserByUserName(String username);

  public UserModel getUserByUserId(UUID userId);

  public Users getUserEntityByUserId(UUID userId);

  public UserModel addUser(UserModel user);

  public UserModel editUser(UserModel user);

  public boolean addFollowing(String followerUsername, String username);

  public boolean removeFollowing(String followerUsername, String username);

  public List<UserModel> getFollowers(UUID userId);

  public List<UserModel> getFollowings(UUID userId);
}
