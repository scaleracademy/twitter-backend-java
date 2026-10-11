/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2026 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package xyz.subho.clone.twitter.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.model.UserModel;
import xyz.subho.clone.twitter.service.PostService;
import xyz.subho.clone.twitter.service.UserService;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {

  @Mock private PostService postService;
  @Mock private UserService userService;

  private PostController controller;
  private final UUID postId = UUID.randomUUID();
  private final UUID userId = UUID.randomUUID();
  private final Principal principal = () -> "alice";

  @BeforeEach
  void setUp() {
    controller = new PostController(postService, userService);
  }

  @Test
  void readEndpointsDelegateToTheService() {
    PostModel post = post();
    when(postService.getAllPosts(any())).thenReturn(new PageImpl<>(List.of(post)));
    when(postService.getPost(postId)).thenReturn(post);
    when(postService.getReplies(postId, PageRequest.of(0, 10)))
        .thenReturn(new PageImpl<>(List.of(post)));

    assertThat(controller.getAllPosts(PageRequest.of(0, 10)).getBody()).isNotEmpty();
    assertThat(controller.getPost(postId).getBody()).isEqualTo(post);
    assertThat(controller.getReplies(postId, PageRequest.of(0, 10)).getBody()).isNotEmpty();
  }

  @Test
  void addPostReturnsUnauthorizedWhenPrincipalHasNoProfile() {
    when(userService.getUserByUserName("alice")).thenReturn(null);

    assertThat(controller.addPost(post(), principal).getStatusCode())
        .isEqualTo(HttpStatus.UNAUTHORIZED);
    verify(postService, never()).addPost(any());
  }

  @Test
  void addPostReplacesClientOwnedIdentityAndCounters() {
    PostModel request = post();
    when(userService.getUserByUserName("alice"))
        .thenReturn(
            new UserModel(userId, "alice", "Alice", null, "a@e.test", null, null, 0L, 0L, false));
    when(postService.addPost(any())).thenReturn(request);

    assertThat(controller.addPost(request, principal).getStatusCode())
        .isEqualTo(HttpStatus.CREATED);
    verify(postService)
        .addPost(
            new PostModel(
                null, "hello", userId, List.of(), null, null, null, null, null, List.of(),
                List.of()));
  }

  @Test
  void deleteRequiresAProfileAndDelegatesKnownProfiles() {
    when(userService.getUserByUserName("alice")).thenReturn(null);
    assertThat(controller.deletePost(postId, principal).getStatusCode())
        .isEqualTo(HttpStatus.UNAUTHORIZED);
    verify(postService, never()).deletePost(any(), any());

    when(userService.getUserByUserName("alice"))
        .thenReturn(
            new UserModel(userId, "alice", "Alice", null, "a@e.test", null, null, 0L, 0L, false));
    controller.deletePost(postId, principal);
    verify(postService).deletePost(postId, userId);
  }

  @Test
  void likeAndUnlikeRequireAProfile() {
    when(userService.getUserByUserName("alice")).thenReturn(null);
    assertThat(controller.likePost(postId, principal).getStatusCode())
        .isEqualTo(HttpStatus.UNAUTHORIZED);
    assertThat(controller.removeLikePost(postId, principal).getStatusCode())
        .isEqualTo(HttpStatus.UNAUTHORIZED);

    when(userService.getUserByUserName("alice"))
        .thenReturn(
            new UserModel(userId, "alice", "Alice", null, "a@e.test", null, null, 0L, 0L, false));
    when(postService.addLike(postId, userId)).thenReturn(1L);
    when(postService.removeLike(postId, userId)).thenReturn(0L);
    assertThat(controller.likePost(postId, principal).getStatusCode())
        .isEqualTo(HttpStatus.CREATED);
    assertThat(controller.removeLikePost(postId, principal).getStatusCode())
        .isEqualTo(HttpStatus.OK);
  }

  private PostModel post() {
    return new PostModel(
        postId, "hello", userId, List.of(), 9L, 8L, null, null, null, List.of(), List.of());
  }
}
