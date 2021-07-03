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
import javax.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity(name = "Likes")
@Table(name = "likes")
@Data
@RequiredArgsConstructor
public class Likes implements Serializable {

  private static final long serialVersionUID = 10299532548L;

  @EmbeddedId private UserPostsId id;

  @ManyToOne
  @MapsId("postId")
  private Posts posts;

  @ManyToOne
  @MapsId("userId")
  private Users users;

  @CreationTimestamp private Date createdAt = new Date();

  @UpdateTimestamp private Date updatedAt = new Date();

  /**
   * @param posts
   * @param users
   */
  public Likes(Posts posts, Users users) {
    this.id = new UserPostsId(users.getId(), posts.getId());
    this.posts = posts;
    this.users = users;
  }
}
