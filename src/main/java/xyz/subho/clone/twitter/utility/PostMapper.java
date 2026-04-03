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

package xyz.subho.clone.twitter.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import xyz.subho.clone.twitter.entity.Posts;
import xyz.subho.clone.twitter.model.PostModel;

@Component("PostMapper")
public class PostMapper implements Mapper<Posts, PostModel> {

  @Override
  public PostModel transform(Posts post) {
    return new PostModel(
        post.getId(),
        post.getText(),
        post.getUsers().getId(),
        new ArrayList<>(post.getImages().keySet()),
        post.getLikeCount(),
        post.getRepostCount(),
        post.getOriginalPostId(),
        post.getReplyToId(),
        post.getTimestamp(),
        new ArrayList<>(post.getHashtags().keySet()),
        new ArrayList<>(post.getMentions().keySet()));
  }

  @Override
  public Posts transformBack(PostModel postModel) {
    Posts post = new Posts();
    post.setId(postModel.id());
    post.setText(postModel.text());
    Map<String, Date> hashtags = new HashMap<>();
    Map<String, Date> mentions = new HashMap<>();
    postModel.hashtags().forEach(tag -> hashtags.put(tag, new Date()));
    postModel.mentions().forEach(mention -> mentions.put(mention, new Date()));
    post.setHashtags(hashtags);
    post.setMentions(mentions);
    post.setLikeCount(null != postModel.likeCount() ? postModel.likeCount() : 0L);
    post.setRepostCount(null != postModel.repostCount() ? postModel.repostCount() : 0L);
    post.setOriginalPostId(postModel.originalPostId());
    post.setReplyToId(postModel.replyToId());
    return post;
  }
}
