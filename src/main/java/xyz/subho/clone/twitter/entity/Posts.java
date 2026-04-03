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

package xyz.subho.clone.twitter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "posts")
public class Posts {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(length = 240)
  private String text;

  @ManyToOne
  @JoinColumn(
      name = "users_id",
      columnDefinition = "BINARY(16)",
      updatable = false,
      nullable = false)
  @CreatedBy
  private Users users;

  @ElementCollection private Map<String, Date> images = new HashMap<>(4); // maximum of 4 images

  @Column(name = "like_count", columnDefinition = "BIGINT(20) default '0'", nullable = false)
  private Long likeCount = 0L;

  @Column(name = "repost_count", columnDefinition = "BIGINT(20) default '0'", nullable = false)
  private Long repostCount = 0L;

  @Column(name = "orig_post_id")
  private UUID originalPostId;

  @Column(name = "reply_to_id")
  private UUID replyToId;

  @CreationTimestamp private Date timestamp;

  @UpdateTimestamp private Date updatedAt;

  @ElementCollection private Map<String, Date> hashtags = new HashMap<>();

  @ElementCollection private Map<String, Date> mentions = new HashMap<>();

  @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<HashtagPosts> postHashtags = new ArrayList<>();

  @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Likes> postLikes = new ArrayList<>();

  public Posts() {}

  public long incrementLikeCount() {
    return ++likeCount;
  }

  public long decrementLikeCount() {
    return (likeCount < 1) ? 0 : --likeCount;
  }

  public long incrementRepostCount() {
    return ++repostCount;
  }

  public long decrementRepostCount() {
    return (repostCount < 1) ? 0 : --repostCount;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Users getUsers() {
    return users;
  }

  public void setUsers(Users users) {
    this.users = users;
  }

  public Map<String, Date> getImages() {
    return images;
  }

  public void setImages(Map<String, Date> images) {
    this.images = images;
  }

  public Long getLikeCount() {
    return likeCount;
  }

  public void setLikeCount(Long likeCount) {
    this.likeCount = likeCount;
  }

  public Long getRepostCount() {
    return repostCount;
  }

  public void setRepostCount(Long repostCount) {
    this.repostCount = repostCount;
  }

  public UUID getOriginalPostId() {
    return originalPostId;
  }

  public void setOriginalPostId(UUID originalPostId) {
    this.originalPostId = originalPostId;
  }

  public UUID getReplyToId() {
    return replyToId;
  }

  public void setReplyToId(UUID replyToId) {
    this.replyToId = replyToId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Map<String, Date> getHashtags() {
    return hashtags;
  }

  public void setHashtags(Map<String, Date> hashtags) {
    this.hashtags = hashtags;
  }

  public Map<String, Date> getMentions() {
    return mentions;
  }

  public void setMentions(Map<String, Date> mentions) {
    this.mentions = mentions;
  }

  public List<HashtagPosts> getPostHashtags() {
    return postHashtags;
  }

  public void setPostHashtags(List<HashtagPosts> postHashtags) {
    this.postHashtags = postHashtags;
  }

  public List<Likes> getPostLikes() {
    return postLikes;
  }

  public void setPostLikes(List<Likes> postLikes) {
    this.postLikes = postLikes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Posts posts = (Posts) o;
    return Objects.equals(id, posts.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Posts{" + "id=" + id + ", text='" + text + '\'' + '}';
  }
}
