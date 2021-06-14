package xyz.subho.clone.twitter.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "posts")
public class Posts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(length = 240)
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private Users users;
	
	@ElementCollection
	private Set<String> images = new HashSet<>(4);	//maximum of 4 images
	
	@Column(name = "like_count")
	private Long likeCount;
	
	@Column(name = "repost_count")
	private Long repostCount;
	
	@Column(name = "orig_post_id")
	private UUID originalPostId;
	
	@Column(name = "reply_to_id")
	private UUID replyToId;
	
	private Date timestamp;
	
	@ElementCollection
	private Set<String> hashtags = new HashSet<>();
	
	@ElementCollection
	private Map<String, UUID> mentions = new HashMap<>();
	
	@OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<HashtagPosts> postHashtags = new ArrayList<>();

	@OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Likes> postLikes = new ArrayList<>();

}
