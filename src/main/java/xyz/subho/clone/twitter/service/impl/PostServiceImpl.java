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
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.subho.clone.twitter.entity.HashtagPosts;
import xyz.subho.clone.twitter.entity.Likes;
import xyz.subho.clone.twitter.entity.Posts;
import xyz.subho.clone.twitter.exception.ResourceNotFoundException;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.repository.HashtagPostsRepository;
import xyz.subho.clone.twitter.repository.LikesRepository;
import xyz.subho.clone.twitter.repository.PostsRepository;
import xyz.subho.clone.twitter.repository.UsersRepository;
import xyz.subho.clone.twitter.service.HashtagService;
import xyz.subho.clone.twitter.service.PostService;
import xyz.subho.clone.twitter.utility.PostMapper;

@Service
public class PostServiceImpl implements PostService {

  private final PostsRepository postsRepository;
  private final HashtagPostsRepository hashtagPostRepository;
  private final HashtagService hashtagService;
  private final LikesRepository likeRepository;
  private final UsersRepository usersRepository;
  private final PostMapper postMapper;

  public PostServiceImpl(
      PostsRepository postsRepository,
      HashtagPostsRepository hashtagPostRepository,
      HashtagService hashtagService,
      LikesRepository likeRepository,
      UsersRepository usersRepository,
      PostMapper postMapper) {
    this.postsRepository = postsRepository;
    this.hashtagPostRepository = hashtagPostRepository;
    this.hashtagService = hashtagService;
    this.likeRepository = likeRepository;
    this.usersRepository = usersRepository;
    this.postMapper = postMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public @NonNull Page<PostModel> getAllPosts(@NonNull Pageable pageable) {
    var postsPage = postsRepository.findAll(pageable);
    return postsPage.map(postMapper::toModel);
  }

  @Override
  @Transactional(readOnly = true)
  public @Nullable PostModel getPost(@NonNull UUID postId) {

    var post = postsRepository.findById(postId);
    if (post.isPresent()) return postMapper.toModel(post.get());
    throw new ResourceNotFoundException("Post ID is Invalid");
  }

  @Override
  @Transactional
  public @NonNull PostModel addPost(@NonNull PostModel postModel) {

    List<HashtagPosts> hashtagPosts = new ArrayList<>();
    validateReferencedPost(postModel.originalPostId(), "Original post ID is invalid");
    validateReferencedPost(postModel.replyToId(), "Reply post ID is invalid");
    var post = postMapper.toEntity(postModel);
    post.setUsers(usersRepository.getReferenceById(postModel.userId()));
    var savedPost = postsRepository.save(post);
    var hashtags = hashtagService.getHashtagsByTags(postModel.hashtags());
    hashtags.forEach(
        hashtag -> {
          HashtagPosts hashtagPost = new HashtagPosts();
          hashtagPost.setHashtags(hashtag);
          hashtagPost.setPosts(savedPost);
          hashtagPosts.add(hashtagPost);
        });
    savedPost.setPostHashtags(hashtagPosts);
    hashtagPostRepository.saveAll(hashtagPosts);
    return postMapper.toModel(savedPost);
  }

  @Override
  @Transactional
  public boolean deletePost(@NonNull UUID postId, @NonNull UUID userId) {

    var post = getPostEntity(postId);
    if (!post.getUsers().getId().equals(userId)) {
      throw new AccessDeniedException("You cannot delete another user's post");
    }
    postsRepository.clearReferences(postId);
    postsRepository.delete(post);
    return true;
  }

  @Override
  @Transactional
  public long addLike(@NonNull UUID postId, @NonNull UUID userId) {

    var post = getPostEntityForUpdate(postId);
    var user =
        usersRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    if (!likeRepository.existsByPostsAndUsers(post, user)) {
      var likeMapping = new Likes();
      likeMapping.setPosts(post);
      likeMapping.setUsers(user);
      likeRepository.save(likeMapping);
      post.incrementLikeCount();
      postsRepository.save(post);
    }
    return post.getLikeCount();
  }

  @Override
  @Transactional
  public long removeLike(@NonNull UUID postId, @NonNull UUID userId) {

    var post = getPostEntityForUpdate(postId);
    var user =
        usersRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    if (likeRepository.deleteByPostsAndUsers(post, user) > 0) {
      post.decrementLikeCount();
      postsRepository.save(post);
    }
    return post.getLikeCount();
  }

  private Posts getPostEntity(@NonNull UUID postId) {
    return postsRepository
        .findById(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post ID is Invalid"));
  }

  private Posts getPostEntityForUpdate(@NonNull UUID postId) {
    return postsRepository
        .findByIdForUpdate(postId)
        .orElseThrow(() -> new ResourceNotFoundException("Post ID is Invalid"));
  }

  @Override
  @Transactional(readOnly = true)
  public @NonNull Page<PostModel> getReplies(@NonNull UUID postId, @NonNull Pageable pageable) {
    var repliesPage = postsRepository.findByReplyToId(postId, pageable);
    return repliesPage.map(postMapper::toModel);
  }

  private void validateReferencedPost(UUID postId, String message) {
    if (postId != null) {
      postsRepository
          .findByIdForUpdate(postId)
          .orElseThrow(() -> new ResourceNotFoundException(message));
    }
  }
}
