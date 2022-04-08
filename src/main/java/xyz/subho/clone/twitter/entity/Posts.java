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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;

@Entity(name = "Posts")
@Table(name = "posts")
@Data
public class Posts implements Serializable {

  private static final long serialVersionUID = 3619911923687L;

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

  @Column(name = "orig_post_id", columnDefinition = "BINARY(16)")
  private UUID originalPostId;

  @Column(name = "reply_to_id", columnDefinition = "BINARY(16)")
  private UUID replyToId;

  @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Mentions> postMentions = new ArrayList<>();

  @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<HashtagPosts> postHashtags = new ArrayList<>();

  @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Likes> postLikes = new ArrayList<>();

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

  public void addHashtags(List<Hashtags> hashtags) {

    hashtags.forEach(
        tag -> {
          tag.incrementRecentPostCount();
          var mapping = new HashtagPosts(tag, this);
          this.postHashtags.add(mapping);
        });
  }

  public void addMentions(List<Users> mentions) {

    mentions.forEach(
        user -> {
          var mapping = new Mentions(this, user);
          this.postMentions.add(mapping);
        });
  }

  public long incrementLikeCount(Users likedByUser) {

    var likes = new Likes(this, likedByUser);
    this.postLikes.add(likes);

    return this.postLikes.stream()
        .filter(thisPost -> thisPost.getPosts().equals(this))
        .collect(Collectors.toList())
        .size();
  }

  public long decrementLikeCount(Users unlikedByUser) {

    List<Likes> toBeDeleted =
        postLikes.stream()
            .filter(like -> like.getUsers().equals(unlikedByUser) && like.getPosts().equals(this))
            .collect(Collectors.toList());
    postLikes.removeAll(toBeDeleted);

    return this.postLikes.stream()
        .filter(thisPost -> thisPost.getPosts().equals(this))
        .collect(Collectors.toList())
        .size();
  }

  public long incrementRepostCount() {
    return ++repostCount;
  }

  public long decrementRepostCount() {

    if (repostCount < 1L) {
      repostCount = 0L;
    } else {
      --repostCount;
    }
    return repostCount;
  }

  public int addImages(List<String> imageUrls) {

    imageUrls.forEach(url -> this.images.put(url, new Date()));
    return this.images.size();
  }

  public List<String> getImagesAsList() {

    List<String> imageUrls = new ArrayList<>(4);
    this.images.forEach((url, date) -> imageUrls.add(url));
    return imageUrls;
  }
}
