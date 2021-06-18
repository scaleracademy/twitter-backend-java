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
@Table(name = "likes")
@NoArgsConstructor
public class Likes {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "posts_id")
  private Posts posts;

  @ManyToOne(targetEntity = Users.class)
  @JoinColumn(name = "users_id")
  private Users users;

  /**
   * @param id
   * @param posts
   * @param users
   */
  public Likes(UUID id, Posts posts, Users users) {

    this.id = id;
    this.posts = posts;
    this.users = users;
  }

  /**
   * @param posts
   * @param users
   */
  public Likes(Posts posts, Users users) {

    this.posts = posts;
    this.users = users;
  }

  /** @return the id */
  public UUID getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(UUID id) {
    this.id = id;
  }

  /** @return the posts */
  public Posts getPosts() {
    return posts;
  }

  /** @param posts the posts to set */
  public void setPosts(Posts posts) {
    this.posts = posts;
  }

  /** @return the users */
  public Users getUsers() {
    return users;
  }

  /** @param users the users to set */
  public void setUsers(Users users) {
    this.users = users;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, posts, users);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Likes)) return false;
    Likes other = (Likes) obj;
    return Objects.equals(id, other.id)
        && Objects.equals(posts, other.posts)
        && Objects.equals(users, other.users);
  }

  @Override
  public String toString() {
    return "Likes [id=" + id + ", posts=" + posts + ", users=" + users + "]";
  }
}
