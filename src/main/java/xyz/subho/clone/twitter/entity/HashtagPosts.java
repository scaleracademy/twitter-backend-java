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

@Entity
@Table(name = "hashtag_posts")
public class HashtagPosts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "hashtags_id")
	private Hashtags hashtags;
	
	@ManyToOne
	@JoinColumn(name = "posts_id")
	private Posts posts;

	/**
	 * @param id
	 * @param hashtags
	 * @param posts
	 */
	public HashtagPosts(UUID id, Hashtags hashtags, Posts posts) {
		
		this.id = id;
		this.hashtags = hashtags;
		this.posts = posts;
	}
	
	/**
	 * @param hashtags
	 * @param posts
	 */
	public HashtagPosts(Hashtags hashtags, Posts posts) {
		
		this.hashtags = hashtags;
		this.posts = posts;
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
	 * @return the hashtags
	 */
	protected Hashtags getHashtags() {
		return hashtags;
	}

	/**
	 * @param hashtags the hashtags to set
	 */
	protected void setHashtags(Hashtags hashtags) {
		this.hashtags = hashtags;
	}

	/**
	 * @return the posts
	 */
	protected Posts getPosts() {
		return posts;
	}

	/**
	 * @param posts the posts to set
	 */
	protected void setPosts(Posts posts) {
		this.posts = posts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hashtags, id, posts);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof HashtagPosts))
			return false;
		HashtagPosts other = (HashtagPosts) obj;
		return Objects.equals(hashtags, other.hashtags) && Objects.equals(id, other.id)
				&& Objects.equals(posts, other.posts);
	}

	@Override
	public String toString() {
		return "HashtagPosts [id=" + id + ", hashtags=" + hashtags + ", posts=" + posts + "]";
	}
}
