package xyz.subho.clone.twitter.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(unique = true, length = 30)
	private String username;
	
	private String password;
	
	@Column(length = 50)
	private String name;
	
	private String avatar;
	
	@Column(length = 240)
	private String bio;
	
	@Column(name = "follower_count")
	private String followerCount;
	
	@Column(name = "following_count")
	private String followingCount;
	
	private Boolean verified;
	
	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Likes> userLikes = new ArrayList<>();
	
	@ManyToMany(mappedBy = "followee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<UserFollowings> userFollowers = new ArrayList<>();
	
	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Posts> userPosts = new ArrayList<>();

	/**
	 * @param id
	 * @param username
	 * @param password
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
	public Users(UUID id, String username, String password, String name, String avatar, String bio,
			String followerCount, String followingCount, Boolean verified, List<Likes> userLikes,
			List<UserFollowings> userFollowers, List<Posts> userPosts) {
		
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.avatar = avatar;
		this.bio = bio;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
		this.verified = verified;
		this.userLikes = userLikes;
		this.userFollowers = userFollowers;
		this.userPosts = userPosts;
	}
	
	/**
	 * @param username
	 * @param password
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
	public Users(String username, String password, String name, String avatar, String bio,
			String followerCount, String followingCount, Boolean verified, List<Likes> userLikes,
			List<UserFollowings> userFollowers, List<Posts> userPosts) {
		
		this.username = username;
		this.password = password;
		this.name = name;
		this.avatar = avatar;
		this.bio = bio;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
		this.verified = verified;
		this.userLikes = userLikes;
		this.userFollowers = userFollowers;
		this.userPosts = userPosts;
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
	 * @return the username
	 */
	protected String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	protected void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	protected String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	protected void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	protected String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the avatar
	 */
	protected String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	protected void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the bio
	 */
	protected String getBio() {
		return bio;
	}

	/**
	 * @param bio the bio to set
	 */
	protected void setBio(String bio) {
		this.bio = bio;
	}

	/**
	 * @return the followerCount
	 */
	protected String getFollowerCount() {
		return followerCount;
	}

	/**
	 * @param followerCount the followerCount to set
	 */
	protected void setFollowerCount(String followerCount) {
		this.followerCount = followerCount;
	}

	/**
	 * @return the followingCount
	 */
	protected String getFollowingCount() {
		return followingCount;
	}

	/**
	 * @param followingCount the followingCount to set
	 */
	protected void setFollowingCount(String followingCount) {
		this.followingCount = followingCount;
	}

	/**
	 * @return the verified
	 */
	protected Boolean getVerified() {
		return verified;
	}

	/**
	 * @param verified the verified to set
	 */
	protected void setVerified(Boolean verified) {
		this.verified = verified;
	}

	/**
	 * @return the userLikes
	 */
	protected List<Likes> getUserLikes() {
		return userLikes;
	}

	/**
	 * @param userLikes the userLikes to set
	 */
	protected void setUserLikes(List<Likes> userLikes) {
		this.userLikes = userLikes;
	}

	/**
	 * @return the userFollowers
	 */
	protected List<UserFollowings> getUserFollowers() {
		return userFollowers;
	}

	/**
	 * @param userFollowers the userFollowers to set
	 */
	protected void setUserFollowers(List<UserFollowings> userFollowers) {
		this.userFollowers = userFollowers;
	}

	/**
	 * @return the userPosts
	 */
	protected List<Posts> getUserPosts() {
		return userPosts;
	}

	/**
	 * @param userPosts the userPosts to set
	 */
	protected void setUserPosts(List<Posts> userPosts) {
		this.userPosts = userPosts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(avatar, bio, followerCount, followingCount, id, name, password, userFollowers, userLikes,
				userPosts, username, verified);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Users))
			return false;
		Users other = (Users) obj;
		return Objects.equals(avatar, other.avatar) && Objects.equals(bio, other.bio)
				&& Objects.equals(followerCount, other.followerCount)
				&& Objects.equals(followingCount, other.followingCount) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password)
				&& Objects.equals(userFollowers, other.userFollowers) && Objects.equals(userLikes, other.userLikes)
				&& Objects.equals(userPosts, other.userPosts) && Objects.equals(username, other.username)
				&& Objects.equals(verified, other.verified);
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", avatar="
				+ avatar + ", bio=" + bio + ", followerCount=" + followerCount + ", followingCount=" + followingCount
				+ ", verified=" + verified + ", userLikes=" + userLikes + ", userFollowers=" + userFollowers
				+ ", userPosts=" + userPosts + "]";
	}
	
}
