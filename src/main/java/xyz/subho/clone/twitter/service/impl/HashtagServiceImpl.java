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
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.subho.clone.twitter.entity.Hashtags;
import xyz.subho.clone.twitter.model.HashtagModel;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.repository.HashtagPostsRepository;
import xyz.subho.clone.twitter.repository.HashtagsRepository;
import xyz.subho.clone.twitter.service.HashtagService;
import xyz.subho.clone.twitter.utility.HashtagMapper;
import xyz.subho.clone.twitter.utility.PostMapper;

@Service
public class HashtagServiceImpl implements HashtagService {

  private final HashtagsRepository hashtagsRepository;
  private final HashtagPostsRepository hashtagPostsRepository;
  private final HashtagMapper hashtagMapper;
  private final PostMapper postMapper;

  public HashtagServiceImpl(
      HashtagsRepository hashtagsRepository,
      HashtagPostsRepository hashtagPostsRepository,
      HashtagMapper hashtagMapper,
      PostMapper postMapper) {
    this.hashtagsRepository = hashtagsRepository;
    this.hashtagPostsRepository = hashtagPostsRepository;
    this.hashtagMapper = hashtagMapper;
    this.postMapper = postMapper;
  }

  @Override
  public @NonNull Page<HashtagModel> getHashtags(@NonNull Pageable pageable) {
    var hashtagsPage = hashtagsRepository.findAll(pageable);
    return hashtagsPage.map(hashtagMapper::toModel);
  }

  @Override
  public @NonNull Page<PostModel> getPosts(@NonNull String tag, @NonNull Pageable pageable) {
    var hashtag = hashtagsRepository.findByTag(tag);
    if (null == hashtag) {
      return Page.empty();
    }
    var hashtagPostsPage = hashtagPostsRepository.findByHashtags(hashtag, pageable);
    return hashtagPostsPage.map(hp -> postMapper.toModel(hp.getPosts()));
  }

  @Override
  @Transactional
  public @NonNull List<Hashtags> getHashtagsByTags(@NonNull List<String> tags) {
    List<String> normalizedTags =
        tags.stream()
            .filter(tag -> tag != null && !tag.isBlank())
            .map(tag -> tag.trim().toLowerCase(Locale.ROOT))
            .distinct()
            .toList();
    if (normalizedTags.isEmpty()) {
      return List.of();
    }

    List<Hashtags> existingHashtags = hashtagsRepository.findByTagIn(normalizedTags);
    Set<String> existingTags =
        existingHashtags.stream().map(Hashtags::getTag).collect(Collectors.toSet());
    List<String> tagsToCreate =
        normalizedTags.stream().filter(tag -> !existingTags.contains(tag)).toList();

    if (!existingTags.isEmpty()) {
      hashtagsRepository.incrementRecentPostCount(new ArrayList<>(existingTags));
    }
    if (!tagsToCreate.isEmpty()) {
      List<Hashtags> newHashtags =
          tagsToCreate.stream()
              .map(
                  tag -> {
                    Hashtags hashtag = new Hashtags();
                    hashtag.setTag(tag);
                    hashtag.setRecentPostCount(1L);
                    return hashtag;
                  })
              .toList();
      hashtagsRepository.saveAll(newHashtags);
    }
    return hashtagsRepository.findByTagIn(normalizedTags);
  }
}
