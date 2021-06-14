package xyz.subho.clone.twitter.entity;

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
}
