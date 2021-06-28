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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

@Entity(name = "Posts")
@Table(name = "posts")
@Data
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

  public long incrementLikeCount() {
    return ++likeCount;
  }

  public long decrementLikeCount() {
    if (likeCount < 1L) {
      likeCount = 0L;
    } else {
      --likeCount;
    }
    return likeCount;
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
}
