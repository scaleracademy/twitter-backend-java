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

package xyz.subho.clone.twitter.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import xyz.subho.clone.twitter.entity.Likes;
import xyz.subho.clone.twitter.entity.Posts;
import xyz.subho.clone.twitter.entity.Users;

public class UserResponse {

  private UUID id;
  private String username;
  private String name;
  private String avatar;
  private String bio;
  private Long followerCount;
  private Long followingCount;
  private Boolean verified;
  private List<Likes> userLikes = new ArrayList<>();
  private List<Posts> userPosts = new ArrayList<>();

  /** @param Users (Entity) */
  public UserResponse(Users userEntity) {

    this.id = userEntity.getId();
    this.username = userEntity.getUsername();
    this.name = userEntity.getName();
    this.avatar = userEntity.getAvatar();
    this.bio = userEntity.getBio();
    this.followerCount = userEntity.getFollowerCount();
    this.followingCount = userEntity.getFollowingCount();
    this.verified = userEntity.getVerified();
    this.userLikes = userEntity.getUserLikes();
    this.userPosts = userEntity.getUserPosts();
  }

  /**
   * @param id
   * @param username
   * @param name
   * @param avatar
   * @param bio
   * @param followerCount
   * @param followingCount
   * @param verified
   * @param userLikes
   * @param userFollowers
   * @param userPosts
   */
  public UserResponse(
      UUID id,
      String username,
      String name,
      String avatar,
      String bio,
      Long followerCount,
      Long followingCount,
      Boolean verified,
      List<Likes> userLikes,
      List<Posts> userPosts) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.avatar = avatar;
    this.bio = bio;
    this.followerCount = followerCount;
    this.followingCount = followingCount;
    this.verified = verified;
    this.userLikes = userLikes;
    this.userPosts = userPosts;
  }

  /** @return the id */
  public UUID getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(UUID id) {
    this.id = id;
  }

  /** @return the username */
  public String getUsername() {
    return username;
  }

  /** @param username the username to set */
  public void setUsername(String username) {
    this.username = username;
  }

  /** @return the name */
  public String getName() {
    return name;
  }

  /** @param name the name to set */
  public void setName(String name) {
    this.name = name;
  }

  /** @return the avatar */
  public String getAvatar() {
    return avatar;
  }

  /** @param avatar the avatar to set */
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  /** @return the bio */
  public String getBio() {
    return bio;
  }

  /** @param bio the bio to set */
  public void setBio(String bio) {
    this.bio = bio;
  }

  /** @return the followerCount */
  public Long getFollowerCount() {
    return followerCount;
  }

  /** @param followerCount the followerCount to set */
  public void setFollowerCount(Long followerCount) {
    this.followerCount = followerCount;
  }

  /** @return the followingCount */
  public Long getFollowingCount() {
    return followingCount;
  }

  /** @param followingCount the followingCount to set */
  public void setFollowingCount(Long followingCount) {
    this.followingCount = followingCount;
  }

  /** @return the verified */
  public Boolean getVerified() {
    return verified;
  }

  /** @param verified the verified to set */
  public void setVerified(Boolean verified) {
    this.verified = verified;
  }

  /** @return the userLikes */
  public List<Likes> getUserLikes() {
    return userLikes;
  }

  /** @param userLikes the userLikes to set */
  public void setUserLikes(List<Likes> userLikes) {
    this.userLikes = userLikes;
  }

  /** @return the userPosts */
  public List<Posts> getUserPosts() {
    return userPosts;
  }

  /** @param userPosts the userPosts to set */
  public void setUserPosts(List<Posts> userPosts) {
    this.userPosts = userPosts;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        avatar,
        bio,
        followerCount,
        followingCount,
        id,
        name,
        userLikes,
        userPosts,
        username,
        verified);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof UserResponse)) return false;
    UserResponse other = (UserResponse) obj;
    return Objects.equals(avatar, other.avatar)
        && Objects.equals(bio, other.bio)
        && Objects.equals(followerCount, other.followerCount)
        && Objects.equals(followingCount, other.followingCount)
        && Objects.equals(id, other.id)
        && Objects.equals(name, other.name)
        && Objects.equals(userLikes, other.userLikes)
        && Objects.equals(userPosts, other.userPosts)
        && Objects.equals(username, other.username)
        && Objects.equals(verified, other.verified);
  }

  @Override
  public String toString() {
    return "UserResponse [id="
        + id
        + ", username="
        + username
        + ", name="
        + name
        + ", avatar="
        + avatar
        + ", bio="
        + bio
        + ", followerCount="
        + followerCount
        + ", followingCount="
        + followingCount
        + ", verified="
        + verified
        + ", userLikes="
        + userLikes
        + ", userPosts="
        + userPosts
        + "]";
  }
}
