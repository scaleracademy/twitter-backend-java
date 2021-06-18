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

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hashtag_posts")
@NoArgsConstructor
public class HashtagPosts {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "hashtags_id")
  private Hashtags hashtags;

  @ManyToOne
  @JoinColumn(name = "posts_id")
  private Posts posts;

  /**
   * @param id
   * @param hashtags
   * @param posts
   */
  public HashtagPosts(UUID id, Hashtags hashtags, Posts posts) {

    this.id = id;
    this.hashtags = hashtags;
    this.posts = posts;
  }

  /**
   * @param hashtags
   * @param posts
   */
  public HashtagPosts(Hashtags hashtags, Posts posts) {

    this.hashtags = hashtags;
    this.posts = posts;
  }

  /** @return the id */
  public UUID getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(UUID id) {
    this.id = id;
  }

  /** @return the hashtags */
  public Hashtags getHashtags() {
    return hashtags;
  }

  /** @param hashtags the hashtags to set */
  public void setHashtags(Hashtags hashtags) {
    this.hashtags = hashtags;
  }

  /** @return the posts */
  public Posts getPosts() {
    return posts;
  }

  /** @param posts the posts to set */
  public void setPosts(Posts posts) {
    this.posts = posts;
  }

  @Override
  public int hashCode() {
    return Objects.hash(hashtags, id, posts);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) return true;
    if (!(obj instanceof HashtagPosts)) return false;
    HashtagPosts other = (HashtagPosts) obj;
    
    return Objects.equals(hashtags, other.hashtags)
        && Objects.equals(id, other.id)
        && Objects.equals(posts, other.posts);
  }

  @Override
  public String toString() {
    return "HashtagPosts [id=" + id + ", hashtags=" + hashtags + ", posts=" + posts + "]";
  }
}
