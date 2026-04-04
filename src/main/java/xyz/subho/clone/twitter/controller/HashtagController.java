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

package xyz.subho.clone.twitter.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.subho.clone.twitter.constant.HashtagV1Constants;
import xyz.subho.clone.twitter.model.HashtagModel;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.service.HashtagService;

@RestController
@RequestMapping(HashtagV1Constants.BASE_PATH)
@Timed(value = "moo.hashtags.timer", description = "Time taken to process hashtag requests")
public class HashtagController {

  private final HashtagService hashtagService;

  public HashtagController(HashtagService hashtagService) {
    this.hashtagService = hashtagService;
  }

  @GetMapping
  @Counted(value = "moo.hashtags.viewed", description = "Number of times hashtags were viewed")
  public Page<HashtagModel> getAllHashtags(Pageable pageable) {
    return hashtagService.getHashtags(pageable);
  }

  @GetMapping(HashtagV1Constants.TAG_POSTS)
  public Page<PostModel> getPosts(@PathVariable("tag") String tag, Pageable pageable) {
    return hashtagService.getPosts(tag, pageable);
  }
}
