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

package xyz.subho.clone.twitter.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "HashtagPosts")
@Table(name = "hashtag_posts")
@Data
@RequiredArgsConstructor
public class HashtagPosts implements Serializable {

  private static final long serialVersionUID = 859441133926L;

  @EmbeddedId private HashtagPostsId id;

  @ManyToOne(targetEntity = Hashtags.class)
  @MapsId("hashtagId")
  private Hashtags hashtags;

  @ManyToOne(targetEntity = Posts.class)
  @MapsId("postId")
  private Posts posts;

  private Date createdAt;

  private Date updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = new Date();
  }

  /**
   * @param hashtags
   * @param posts
   */
  public HashtagPosts(Hashtags hashtags, Posts posts) {
    this.id = new HashtagPostsId(hashtags.getId(), posts.getId());
    this.hashtags = hashtags;
    this.posts = posts;
  }
}
