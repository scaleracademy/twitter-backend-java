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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "hashtags")
public class Hashtags {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(unique = true)
	private String tag;
	
	@Column(name = "recent_post_count")
	private Long recentPostCount;
	
	@OneToMany(mappedBy = "hashtags", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<HashtagPosts> hashtagPosts = new ArrayList<>();

	/**
	 * @param id
	 * @param tag
	 * @param recentPostCount
	 * @param hashtagPosts
	 */
	public Hashtags(UUID id, String tag, Long recentPostCount, List<HashtagPosts> hashtagPosts) {
		
		this.id = id;
		this.tag = tag;
		this.recentPostCount = recentPostCount;
		this.hashtagPosts = hashtagPosts;
	}
	
	/**
	 * @param tag
	 * @param recentPostCount
	 * @param hashtagPosts
	 */
	public Hashtags(String tag, Long recentPostCount, List<HashtagPosts> hashtagPosts) {
		
		this.tag = tag;
		this.recentPostCount = recentPostCount;
		this.hashtagPosts = hashtagPosts;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the recentPostCount
	 */
	public Long getRecentPostCount() {
		return recentPostCount;
	}

	/**
	 * @param recentPostCount the recentPostCount to set
	 */
	public void setRecentPostCount(Long recentPostCount) {
		this.recentPostCount = recentPostCount;
	}

	/**
	 * @return the hashtagPosts
	 */
	public List<HashtagPosts> getHashtagPosts() {
		return hashtagPosts;
	}

	/**
	 * @param hashtagPosts the hashtagPosts to set
	 */
	public void setHashtagPosts(List<HashtagPosts> hashtagPosts) {
		this.hashtagPosts = hashtagPosts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hashtagPosts, id, recentPostCount, tag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Hashtags))
			return false;
		Hashtags other = (Hashtags) obj;
		return Objects.equals(hashtagPosts, other.hashtagPosts) && Objects.equals(id, other.id)
				&& Objects.equals(recentPostCount, other.recentPostCount) && Objects.equals(tag, other.tag);
	}

	@Override
	public String toString() {
		return "Hashtags [id=" + id + ", tag=" + tag + ", recentPostCount=" + recentPostCount + ", hashtagPosts="
				+ hashtagPosts + "]";
	}
	
}
