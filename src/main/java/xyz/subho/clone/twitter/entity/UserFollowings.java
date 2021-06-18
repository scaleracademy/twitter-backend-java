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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_followings")
@NoArgsConstructor
public class UserFollowings {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @OneToOne private Users followee;

  @OneToOne private Users follower;

  /**
   * @param id
   * @param followee
   * @param follower
   */
  public UserFollowings(UUID id, Users followee, Users follower) {

    this.id = id;
    this.followee = followee;
    this.follower = follower;
  }

  /**
   * @param followee
   * @param follower
   */
  public UserFollowings(Users followee, Users follower) {

    this.followee = followee;
    this.follower = follower;
  }

  /** @return the id */
  public UUID getId() {
    return id;
  }

  /** @param id the id to set */
  public void setId(UUID id) {
    this.id = id;
  }

  /** @return the followee */
  public Users getFollowee() {
    return followee;
  }

  /** @param followee the followee to set */
  public void setFollowee(Users followee) {
    this.followee = followee;
  }

  /** @return the follower */
  public Users getFollower() {
    return follower;
  }

  /** @param follower the follower to set */
  public void setFollower(Users follower) {
    this.follower = follower;
  }

  @Override
  public int hashCode() {
    return Objects.hash(followee, follower, id);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof UserFollowings)) return false;
    UserFollowings other = (UserFollowings) obj;
    return Objects.equals(followee, other.followee)
        && Objects.equals(follower, other.follower)
        && Objects.equals(id, other.id);
  }

  @Override
  public String toString() {
    return "UserFollowings [id=" + id + ", followee=" + followee + ", follower=" + follower + "]";
  }
}
