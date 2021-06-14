package xyz.subho.clone.twitter.entity;

import java.util.ArrayList;
import java.util.List;
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
	
}
