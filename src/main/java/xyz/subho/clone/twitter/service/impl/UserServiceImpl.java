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

package xyz.subho.clone.twitter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.model.UserModel;
import xyz.subho.clone.twitter.repository.UsersRepository;
import xyz.subho.clone.twitter.service.UserService;
import xyz.subho.clone.twitter.utility.Mapper;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UsersRepository usersRepository;

  @Autowired
  @Qualifier("UserMapper")
  private Mapper<Users, UserModel> userMapper;

  @Override
  public UserModel getUserByUserName(String username) {
    return userMapper.transform(usersRepository.findByUsername(username));
  }

  @Override
  public UserModel getUserByUserId(UUID userId) {
    return userMapper.transform(usersRepository.getById(userId));
  }

  @Override
  public Users getUserEntityByUserId(UUID userId) {
    return usersRepository.getById(userId);
  }

  @Override
  @Transactional
  public UserModel addUser(UserModel user) {
    Users users = userMapper.transformBack(user);
    return userMapper.transform(usersRepository.save(users));
  }

  @Override
  @Transactional
  public UserModel editUser(UserModel user) {
    Users users = userMapper.transformBack(user);
    return userMapper.transform(usersRepository.save(users));
  }

  @Override
  @Transactional
  public boolean addFollower(UUID followerId, UUID userId) {
    Users user = usersRepository.getById(userId);
    user.setFollower(followerId);
    usersRepository.save(user);
    return true;
  }

  @Override
  public boolean removeFollower(UUID followerId, UUID userId) {
    Users user = usersRepository.getById(userId);
    user.removeFollower(followerId);
    usersRepository.save(user);
    return true;
  }

  @Override
  public List<UserModel> getFollowers(UUID userId) {
    List<UserModel> followers = new ArrayList<>();
    Users user = usersRepository.getById(userId);
    List<Users> users = usersRepository.findAllById(user.getFollower().keySet());
    Optional.ofNullable(users)
        .ifPresent(
            usersList ->
                usersList.forEach(eachUser -> followers.add(userMapper.transform(eachUser))));
    return followers;
  }

  @Override
  public List<UserModel> getFollowings(UUID userId) {
    List<UserModel> followings = new ArrayList<>();
    Users user = usersRepository.getById(userId);
    List<Users> users = usersRepository.findAllById(user.getFollowing().keySet());
    Optional.ofNullable(users)
        .ifPresent(
            usersList ->
                usersList.forEach(eachUser -> followings.add(userMapper.transform(eachUser))));
    return followings;
  }
}
