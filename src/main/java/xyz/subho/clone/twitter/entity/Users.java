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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "Users")
@Table(
    name = "users",
    indexes = {@Index(columnList = "username")})
@Data
public class Users implements Serializable {

  private static final long serialVersionUID = 5509244829545094653L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @Column(unique = true, nullable = false, length = 30, updatable = false)
  private String username;

  @Column(nullable = false, length = 50)
  private String name;

  private String avatar;

  @Column(unique = true)
  private String email;

  @Column(length = 240)
  private String bio;

  @Column(name = "follower_count", columnDefinition = "BIGINT(20) default '0'", nullable = false)
  private Long followerCount = 0L;

  @Column(name = "following_count", columnDefinition = "BIGINT(20) default '0'", nullable = false)
  private Long followingCount = 0L;

  @Column(columnDefinition = "boolean default false", nullable = false)
  private Boolean verified = false;

  @CreationTimestamp private Date createdAt;

  @UpdateTimestamp private Date updatedAt;

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Likes> userLikes = new ArrayList<>();

  @ElementCollection private Map<String, Date> follower = new HashMap<>();

  @ElementCollection private Map<String, Date> following = new HashMap<>();

  @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore
  private List<Posts> userPosts = new ArrayList<>();

  /*
  @OneToOne(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    optional = false)
  @PrimaryKeyJoinColumn
  private UsersAuthenticationDetails usersAuthenticationDetails;*/

  public long setFollower(final String username) {
    follower.put(username, new Date());
    followerCount = Long.valueOf(follower.size());
    return followerCount;
  }

  public long setFollowing(final String username) {
    following.put(username, new Date());
    followingCount = Long.valueOf(following.size());
    return ++followingCount;
  }

  public long removeFollower(final String username) {
    follower.remove(username);
    followerCount = Long.valueOf(follower.size());
    return followerCount;
  }

  public long removeFollowing(final String username) {
    following.remove(username);
    followingCount = Long.valueOf(following.size());
    return followingCount;
  }
}
