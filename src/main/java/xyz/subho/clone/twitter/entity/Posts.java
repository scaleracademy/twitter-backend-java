package xyz.subho.clone.twitter.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

	/**
	 * @param id
	 * @param text
	 * @param users
	 * @param images
	 * @param likeCount
	 * @param repostCount
	 * @param originalPostId
	 * @param replyToId
	 * @param timestamp
	 * @param hashtags
	 * @param mentions
	 * @param postHashtags
	 * @param postLikes
	 */
	public Posts(UUID id, String text, Users users, Set<String> images, Long likeCount, Long repostCount,
			UUID originalPostId, UUID replyToId, Date timestamp, Set<String> hashtags, Map<String, UUID> mentions,
			List<HashtagPosts> postHashtags, List<Likes> postLikes) {
		
		this.id = id;
		this.text = text;
		this.users = users;
		this.images = images;
		this.likeCount = likeCount;
		this.repostCount = repostCount;
		this.originalPostId = originalPostId;
		this.replyToId = replyToId;
		this.timestamp = timestamp;
		this.hashtags = hashtags;
		this.mentions = mentions;
		this.postHashtags = postHashtags;
		this.postLikes = postLikes;
	}

	/**
	 * @param text
	 * @param users
	 * @param images
	 * @param likeCount
	 * @param repostCount
	 * @param originalPostId
	 * @param replyToId
	 * @param timestamp
	 * @param hashtags
	 * @param mentions
	 * @param postHashtags
	 * @param postLikes
	 */
	public Posts(String text, Users users, Set<String> images, Long likeCount, Long repostCount,
			UUID originalPostId, UUID replyToId, Date timestamp, Set<String> hashtags, Map<String, UUID> mentions,
			List<HashtagPosts> postHashtags, List<Likes> postLikes) {
		
		this.text = text;
		this.users = users;
		this.images = images;
		this.likeCount = likeCount;
		this.repostCount = repostCount;
		this.originalPostId = originalPostId;
		this.replyToId = replyToId;
		this.timestamp = timestamp;
		this.hashtags = hashtags;
		this.mentions = mentions;
		this.postHashtags = postHashtags;
		this.postLikes = postLikes;
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
	 * @return the text
	 */
	protected String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	protected void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the users
	 */
	protected Users getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	protected void setUsers(Users users) {
		this.users = users;
	}

	/**
	 * @return the images
	 */
	protected Set<String> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	protected void setImages(Set<String> images) {
		this.images = images;
	}

	/**
	 * @return the likeCount
	 */
	protected Long getLikeCount() {
		return likeCount;
	}

	/**
	 * @param likeCount the likeCount to set
	 */
	protected void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	/**
	 * @return the repostCount
	 */
	protected Long getRepostCount() {
		return repostCount;
	}

	/**
	 * @param repostCount the repostCount to set
	 */
	protected void setRepostCount(Long repostCount) {
		this.repostCount = repostCount;
	}

	/**
	 * @return the originalPostId
	 */
	protected UUID getOriginalPostId() {
		return originalPostId;
	}

	/**
	 * @param originalPostId the originalPostId to set
	 */
	protected void setOriginalPostId(UUID originalPostId) {
		this.originalPostId = originalPostId;
	}

	/**
	 * @return the replyToId
	 */
	protected UUID getReplyToId() {
		return replyToId;
	}

	/**
	 * @param replyToId the replyToId to set
	 */
	protected void setReplyToId(UUID replyToId) {
		this.replyToId = replyToId;
	}

	/**
	 * @return the timestamp
	 */
	protected Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	protected void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the hashtags
	 */
	protected Set<String> getHashtags() {
		return hashtags;
	}

	/**
	 * @param hashtags the hashtags to set
	 */
	protected void setHashtags(Set<String> hashtags) {
		this.hashtags = hashtags;
	}

	/**
	 * @return the mentions
	 */
	protected Map<String, UUID> getMentions() {
		return mentions;
	}

	/**
	 * @param mentions the mentions to set
	 */
	protected void setMentions(Map<String, UUID> mentions) {
		this.mentions = mentions;
	}

	/**
	 * @return the postHashtags
	 */
	protected List<HashtagPosts> getPostHashtags() {
		return postHashtags;
	}

	/**
	 * @param postHashtags the postHashtags to set
	 */
	protected void setPostHashtags(List<HashtagPosts> postHashtags) {
		this.postHashtags = postHashtags;
	}

	/**
	 * @return the postLikes
	 */
	protected List<Likes> getPostLikes() {
		return postLikes;
	}

	/**
	 * @param postLikes the postLikes to set
	 */
	protected void setPostLikes(List<Likes> postLikes) {
		this.postLikes = postLikes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hashtags, id, images, likeCount, mentions, originalPostId, postHashtags, postLikes,
				replyToId, repostCount, text, timestamp, users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Posts))
			return false;
		Posts other = (Posts) obj;
		return Objects.equals(hashtags, other.hashtags) && Objects.equals(id, other.id)
				&& Objects.equals(images, other.images) && Objects.equals(likeCount, other.likeCount)
				&& Objects.equals(mentions, other.mentions) && Objects.equals(originalPostId, other.originalPostId)
				&& Objects.equals(postHashtags, other.postHashtags) && Objects.equals(postLikes, other.postLikes)
				&& Objects.equals(replyToId, other.replyToId) && Objects.equals(repostCount, other.repostCount)
				&& Objects.equals(text, other.text) && Objects.equals(timestamp, other.timestamp)
				&& Objects.equals(users, other.users);
	}

	@Override
	public String toString() {
		return "Posts [id=" + id + ", text=" + text + ", users=" + users + ", images=" + images + ", likeCount="
				+ likeCount + ", repostCount=" + repostCount + ", originalPostId=" + originalPostId + ", replyToId="
				+ replyToId + ", timestamp=" + timestamp + ", hashtags=" + hashtags + ", mentions=" + mentions
				+ ", postHashtags=" + postHashtags + ", postLikes=" + postLikes + "]";
	}
	
}
