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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.subho.clone.twitter.entity.HashtagPosts;
import xyz.subho.clone.twitter.entity.Likes;
import xyz.subho.clone.twitter.entity.Posts;
import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.exception.ErrorSavingEntityToDatabaseException;
import xyz.subho.clone.twitter.exception.ResourceNotFoundException;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.model.UserModel;
import xyz.subho.clone.twitter.repository.HashtagPostsRepository;
import xyz.subho.clone.twitter.repository.LikesRepository;
import xyz.subho.clone.twitter.repository.PostsRepository;
import xyz.subho.clone.twitter.repository.UsersRepository;
import xyz.subho.clone.twitter.service.HashtagService;
import xyz.subho.clone.twitter.service.PostService;
import xyz.subho.clone.twitter.service.UserService;
import xyz.subho.clone.twitter.utility.Mapper;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

  @Autowired private PostsRepository postsRepository;

  @Autowired private HashtagPostsRepository hashtagPostRepository;

  @Autowired private UserService userService;

  @Autowired private HashtagService hashtagService;

  @Autowired private LikesRepository likeRepository;

  @Autowired private UsersRepository usersRepository;

  @Autowired
  @Qualifier("PostMapper")
  private Mapper<Posts, PostModel> postMapper;

  @Autowired
  @Qualifier("UserMapper")
  private Mapper<Users, UserModel> userMapper;

  @Override
  public @NonNull Page<PostModel> getAllPosts(@NonNull Pageable pageable) {
    var postsPage = postsRepository.findAll(pageable);
    return postsPage.map(postMapper::transform);
  }

  @Override
  public @Nullable PostModel getPost(@NonNull UUID postId) {

    var post = postsRepository.findById(postId);
    if (post.isPresent()) return postMapper.transform(post.get());
    throw new ResourceNotFoundException("Post ID is Invalid");
  }

  @Override
  @Transactional
  public @NonNull PostModel addPost(@NonNull PostModel postModel) {

    List<HashtagPosts> hashtagPosts = new ArrayList<>();
    var post = postMapper.transformBack(postModel);
    post.setUsers(usersRepository.getById(postModel.getUserId()));
    Optional.ofNullable(hashtagService.getHashtagsByTags(postModel.getHashtags()))
        .ifPresent(
            hashtags -> {
              hashtags.forEach(
                  hashtag -> {
                    HashtagPosts hashtagPost = new HashtagPosts();
                    hashtagPost.setHashtags(hashtag);
                    hashtagPost.setPosts(post);
                    hashtagPosts.add(hashtagPost);
                  });
            });
    post.setPostHashtags(hashtagPosts);
    hashtagPostRepository.saveAll(hashtagPosts);
    return postMapper.transform(postsRepository.save(post));
  }

  @Override
  @Transactional
  public boolean deletePost(@NonNull UUID postId, @NonNull UUID userId) {

    if (Optional.ofNullable((getPost(postId))).isPresent()) {
      postsRepository.deleteById(postId);
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public long addLike(@NonNull UUID postId, @NonNull UUID userId) {

    var postModel = getPost(postId);
    if (postModel == null) throw new ResourceNotFoundException("Post not found");
    var post = postMapper.transformBack(postModel);
    post.incrementLikeCount();
    var userModel = userService.getUserByUserId(userId);
    if (userModel == null) throw new ResourceNotFoundException("User not found");
    var user = userMapper.transformBack(userModel);

    var likeMapping = new Likes();
    likeMapping.setPosts(post);
    likeMapping.setUsers(user);
    try {
      likeRepository.save(likeMapping);
      postsRepository.save(post);
      return post.getLikeCount();
    } catch (Exception excp) {
      log.error("Cannot Save LIKES Entity");
      throw new ErrorSavingEntityToDatabaseException("Cannot Save to Database");
    }
  }

  @Override
  @Transactional
  public long removeLike(@NonNull UUID postId, @NonNull UUID userId) {

    var postModel = getPost(postId);
    if (postModel == null) throw new ResourceNotFoundException("Post not found");
    var post = postMapper.transformBack(postModel);
    post.decrementLikeCount();
    var userModel = userService.getUserByUserId(userId);
    if (userModel == null) throw new ResourceNotFoundException("User not found");
    var user = userMapper.transformBack(userModel);

    try {
      likeRepository.deleteByPostsAndUsers(post, user);
      postsRepository.save(post);
      return post.getLikeCount();
    } catch (Exception excp) {
      log.error("Cannot Save LIKES Entity");
      throw new ErrorSavingEntityToDatabaseException("Cannot Save to Database");
    }
  }
}
