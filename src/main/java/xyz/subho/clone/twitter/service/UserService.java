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

package xyz.subho.clone.twitter.service;

import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.model.UserModel;

public interface UserService {

  public @Nullable UserModel getUserByUserName(@NonNull String username);

  public @Nullable UserModel getUserByUserId(@NonNull UUID userId);

  public @Nullable Users getUserEntityByUserId(@NonNull UUID userId);

  public @NonNull UserModel addUser(@NonNull UserModel user);

  public @NonNull UserModel editUser(@NonNull UserModel user);

  public boolean addFollower(@NonNull UUID followerId, @NonNull UUID userId);

  public boolean removeFollower(@NonNull UUID followerId, @NonNull UUID userId);

  public @NonNull Page<UserModel> getFollowers(@NonNull UUID userId, @NonNull Pageable pageable);

  public @NonNull Page<UserModel> getFollowings(@NonNull UUID userId, @NonNull Pageable pageable);
}
