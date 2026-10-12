/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2026 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package xyz.subho.clone.twitter.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import xyz.subho.clone.twitter.entity.HashtagPosts;
import xyz.subho.clone.twitter.entity.Hashtags;
import xyz.subho.clone.twitter.entity.Posts;
import xyz.subho.clone.twitter.model.HashtagModel;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.repository.HashtagPostsRepository;
import xyz.subho.clone.twitter.repository.HashtagsRepository;
import xyz.subho.clone.twitter.service.impl.HashtagServiceImpl;
import xyz.subho.clone.twitter.utility.HashtagMapper;
import xyz.subho.clone.twitter.utility.PostMapper;

@ExtendWith(MockitoExtension.class)
class HashtagServiceImplTest {

  @Mock private HashtagsRepository hashtagsRepository;
  @Mock private HashtagPostsRepository hashtagPostsRepository;
  @Mock private HashtagMapper hashtagMapper;
  @Mock private PostMapper postMapper;

  private HashtagServiceImpl service;

  @BeforeEach
  void setUp() {
    service =
        new HashtagServiceImpl(
            hashtagsRepository, hashtagPostsRepository, hashtagMapper, postMapper);
  }

  @Test
  void emptyTagsDoNotTouchTheRepository() {
    assertThat(service.getHashtagsByTags(Arrays.asList(null, " ", ""))).isEmpty();
    verify(hashtagsRepository, never()).findByTagIn(any());
  }

  @Test
  void tagsAreNormalizedCountsIncrementedAndMissingTagsCreated() {
    Hashtags existing = hashtag("java", 4);
    Hashtags created = hashtag("spring", 1);
    when(hashtagsRepository.findByTagIn(List.of("java", "spring")))
        .thenReturn(List.of(existing), List.of(existing, created));

    assertThat(service.getHashtagsByTags(List.of("spring", "Java", "java")))
        .containsExactly(existing, created);
    verify(hashtagsRepository).incrementRecentPostCount(List.of("java"));
    verify(hashtagsRepository).saveAll(any());
  }

  @Test
  void knownTagWithoutMissingTagsStillReturnsTheKnownTag() {
    Hashtags existing = hashtag("java", 4);
    when(hashtagsRepository.findByTagIn(List.of("java"))).thenReturn(List.of(existing));

    assertThat(service.getHashtagsByTags(List.of("Java"))).containsExactly(existing);
    verify(hashtagsRepository, never()).saveAll(any());
  }

  @Test
  void missingTagsAreCreatedWithoutIncrementingExistingTags() {
    when(hashtagsRepository.findByTagIn(List.of("spring"))).thenReturn(List.of(), List.of());

    assertThat(service.getHashtagsByTags(List.of("Spring"))).isEmpty();

    verify(hashtagsRepository, never()).incrementRecentPostCount(any());
    verify(hashtagsRepository).saveAll(any());
  }

  @Test
  void aTagFoundWhileCreatingIsCountedAsExisting() {
    Hashtags concurrentTag = hashtag("spring", 2);
    when(hashtagsRepository.findByTagIn(List.of("spring")))
        .thenReturn(List.of(), List.of(concurrentTag));
    when(hashtagsRepository.findByTagForUpdate("spring")).thenReturn(concurrentTag);

    assertThat(service.getHashtagsByTags(List.of("spring"))).containsExactly(concurrentTag);

    verify(hashtagsRepository).incrementRecentPostCount(List.of("spring"));
    verify(hashtagsRepository, never()).saveAll(any());
  }

  @Test
  void missingTagReturnsAnEmptyPageAndKnownTagMapsPosts() {
    when(hashtagsRepository.findByTag("missing")).thenReturn(null);
    assertThat(service.getPosts("missing", PageRequest.of(0, 10))).isEmpty();

    Hashtags hashtag = hashtag("java", 1);
    Posts post = new Posts();
    HashtagPosts join = new HashtagPosts();
    join.setHashtags(hashtag);
    join.setPosts(post);
    PostModel model =
        new PostModel(
            UUID.randomUUID(),
            "post",
            null,
            List.of(),
            0L,
            0L,
            null,
            null,
            null,
            List.of(),
            List.of());
    when(hashtagsRepository.findByTag("java")).thenReturn(hashtag);
    when(hashtagPostsRepository.findByHashtags(hashtag, PageRequest.of(0, 10)))
        .thenReturn(new PageImpl<>(List.of(join)));
    when(postMapper.toModel(post)).thenReturn(model);

    assertThat(service.getPosts(" JAVA ", PageRequest.of(0, 10)).getContent())
        .containsExactly(model);
  }

  @Test
  void hashtagPagesUseTheMapper() {
    Hashtags hashtag = hashtag("java", 1);
    HashtagModel model = new HashtagModel(UUID.randomUUID(), "java", 1L);
    when(hashtagsRepository.findAll(PageRequest.of(0, 10)))
        .thenReturn(new PageImpl<>(List.of(hashtag)));
    when(hashtagMapper.toModel(hashtag)).thenReturn(model);

    assertThat(service.getHashtags(PageRequest.of(0, 10)).getContent()).containsExactly(model);
  }

  private Hashtags hashtag(String tag, long count) {
    Hashtags hashtag = new Hashtags();
    hashtag.setTag(tag);
    hashtag.setRecentPostCount(count);
    return hashtag;
  }
}
