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

package xyz.subho.clone.twitter.service.impl;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.model.UserModel;
import xyz.subho.clone.twitter.repository.UsersRepository;
import xyz.subho.clone.twitter.service.UserService;
import xyz.subho.clone.twitter.utility.Mapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UsersRepository usersRepository;

  private final PasswordEncoder passwordEncoder;

  @Qualifier("UserMapper")
  private final Mapper<Users, UserModel> userMapper;

  @Override
  public @Nullable UserModel getUserByUserName(@NonNull String username) {
    return userMapper.transform(usersRepository.findByUsername(username));
  }

  @Override
  public @Nullable UserModel getUserByUserId(@NonNull UUID userId) {
    var user = usersRepository.getById(userId);
    return userMapper.transform(user);
  }

  @Override
  public @Nullable Users getUserEntityByUserId(@NonNull UUID userId) {
    return usersRepository.getById(userId);
  }

  @Override
  @Transactional
  public @NonNull UserModel addUser(@NonNull UserModel userModel) {
    var user = userMapper.transformBack(userModel);
    user.setPassword(passwordEncoder.encode(userModel.getPassword()));
    return userMapper.transform(usersRepository.save(user));
  }

  @Override
  @Transactional
  public @NonNull UserModel editUser(@NonNull UserModel userModel) {
    Users users = userMapper.transformBack(userModel);
    return userMapper.transform(usersRepository.save(users));
  }

  @Override
  @Transactional
  public boolean addFollower(@NonNull UUID followerId, @NonNull UUID userId) {
    Users user = usersRepository.getById(userId);
    user.setFollower(followerId);
    usersRepository.save(user);
    return true;
  }

  @Override
  @Transactional
  public boolean removeFollower(@NonNull UUID followerId, @NonNull UUID userId) {
    Users user = usersRepository.getById(userId);
    user.removeFollower(followerId);
    usersRepository.save(user);
    return true;
  }

  @Override
  public @NonNull Page<UserModel> getFollowers(@NonNull UUID userId, @NonNull Pageable pageable) {
    Users user = usersRepository.getById(userId);
    var usersPage = usersRepository.findByIdIn(user.getFollower().keySet(), pageable);
    return usersPage.map(userMapper::transform);
  }

  @Override
  public @NonNull Page<UserModel> getFollowings(@NonNull UUID userId, @NonNull Pageable pageable) {
    Users user = usersRepository.getById(userId);
    var usersPage = usersRepository.findByIdIn(user.getFollowing().keySet(), pageable);
    return usersPage.map(userMapper::transform);
  }
}
