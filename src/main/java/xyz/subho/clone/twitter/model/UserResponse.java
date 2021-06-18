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
import java.util.UUID;
import lombok.Data;
import xyz.subho.clone.twitter.entity.Likes;
import xyz.subho.clone.twitter.entity.Post;
import xyz.subho.clone.twitter.entity.User;

@Data
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
  private List<Post> userPosts = new ArrayList<>();

  /** @param Users (Entity) */
  public UserResponse(User userEntity) {

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
}
