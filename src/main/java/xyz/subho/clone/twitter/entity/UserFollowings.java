package xyz.subho.clone.twitter.entity;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_followings")
public class UserFollowings {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
		
	@OneToOne
	private Users followee;

	@OneToOne
	private Users follower;

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

	/**
	 * @return the id
	 */
	protected UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	protected void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the followee
	 */
	protected Users getFollowee() {
		return followee;
	}

	/**
	 * @param followee the followee to set
	 */
	protected void setFollowee(Users followee) {
		this.followee = followee;
	}

	/**
	 * @return the follower
	 */
	protected Users getFollower() {
		return follower;
	}

	/**
	 * @param follower the follower to set
	 */
	protected void setFollower(Users follower) {
		this.follower = follower;
	}

	@Override
	public int hashCode() {
		return Objects.hash(followee, follower, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UserFollowings))
			return false;
		UserFollowings other = (UserFollowings) obj;
		return Objects.equals(followee, other.followee) && Objects.equals(follower, other.follower)
				&& Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "UserFollowings [id=" + id + ", followee=" + followee + ", follower=" + follower + "]";
	}
	
}
