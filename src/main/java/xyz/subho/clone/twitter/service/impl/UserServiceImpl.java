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
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.subho.clone.twitter.entity.Follow;
import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.model.UserModel;
import xyz.subho.clone.twitter.repository.FollowRepository;
import xyz.subho.clone.twitter.repository.UsersRepository;
import xyz.subho.clone.twitter.service.UserService;
import xyz.subho.clone.twitter.utility.UserMapper;

@Service
public class UserServiceImpl implements UserService {

  private final UsersRepository usersRepository;
  private final FollowRepository followRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public UserServiceImpl(
      UsersRepository usersRepository,
      FollowRepository followRepository,
      PasswordEncoder passwordEncoder,
      UserMapper userMapper) {
    this.usersRepository = usersRepository;
    this.followRepository = followRepository;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
  }

  @Override
  public @Nullable UserModel getUserByUserName(@NonNull String username) {
    return userMapper.toModel(usersRepository.findByUsername(username));
  }

  @Override
  public @Nullable UserModel getUserByUserId(@NonNull UUID userId) {
    var user = usersRepository.getById(userId);
    return userMapper.toModel(user);
  }

  @Override
  public @Nullable Users getUserEntityByUserId(@NonNull UUID userId) {
    return usersRepository.getById(userId);
  }

  @Override
  @Transactional
  public @NonNull UserModel addUser(@NonNull UserModel userModel) {
    var user = userMapper.toEntity(userModel);
    user.setPassword(passwordEncoder.encode(userModel.password()));
    return userMapper.toModel(usersRepository.save(user));
  }

  @Override
  @Transactional
  public @NonNull UserModel editUser(@NonNull UserModel userModel) {
    Users users = userMapper.toEntity(userModel);
    return userMapper.toModel(usersRepository.save(users));
  }

  @Override
  @Transactional
  public boolean addFollower(@NonNull UUID followerId, @NonNull UUID userId) {
    if (followRepository.existsByFollowerAndFollowing(
        usersRepository.getById(followerId), usersRepository.getById(userId))) {
      return false;
    }

    Users user = usersRepository.getById(userId);
    Users follower = usersRepository.getById(followerId);

    Follow follow = new Follow(follower, user);
    followRepository.save(follow);

    user.setFollowerCount(user.getFollowerCount() + 1);
    usersRepository.save(user);

    follower.setFollowingCount(follower.getFollowingCount() + 1);
    usersRepository.save(follower);
    return true;
  }

  @Override
  @Transactional
  public boolean removeFollower(@NonNull UUID followerId, @NonNull UUID userId) {
    Users user = usersRepository.getById(userId);
    Users follower = usersRepository.getById(followerId);

    followRepository.deleteByFollowerAndFollowing(follower, user);

    user.setFollowerCount(Math.max(0, user.getFollowerCount() - 1));
    usersRepository.save(user);

    follower.setFollowingCount(Math.max(0, follower.getFollowingCount() - 1));
    usersRepository.save(follower);
    return true;
  }

  @Override
  public @NonNull Page<UserModel> getFollowers(@NonNull UUID userId, @NonNull Pageable pageable) {
    Users user = usersRepository.getById(userId);
    var followsPage = followRepository.findByFollowing(user, pageable);
    return followsPage.map(f -> userMapper.toModel(f.getFollower()));
  }

  @Override
  public @NonNull Page<UserModel> getFollowings(@NonNull UUID userId, @NonNull Pageable pageable) {
    Users user = usersRepository.getById(userId);
    var followsPage = followRepository.findByFollower(user, pageable);
    return followsPage.map(f -> userMapper.toModel(f.getFollowing()));
  }
}
