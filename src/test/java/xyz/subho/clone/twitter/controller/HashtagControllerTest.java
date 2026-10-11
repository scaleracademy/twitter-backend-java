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
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import xyz.subho.clone.twitter.model.HashtagModel;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.service.HashtagService;

@ExtendWith(MockitoExtension.class)
class HashtagControllerTest {

  @Mock private HashtagService hashtagService;

  @Test
  void endpointsDelegateToHashtagService() {
    HashtagController controller = new HashtagController(hashtagService);
    HashtagModel hashtag = new HashtagModel(null, "java", 1L);
    when(hashtagService.getHashtags(PageRequest.of(0, 10)))
        .thenReturn(new PageImpl<>(List.of(hashtag)));
    when(hashtagService.getPosts("java", PageRequest.of(0, 10)))
        .thenReturn(new PageImpl<>(List.<PostModel>of()));

    assertThat(controller.getAllHashtags(PageRequest.of(0, 10)).getContent())
        .containsExactly(hashtag);
    assertThat(controller.getPosts("java", PageRequest.of(0, 10))).isEmpty();
  }
}
