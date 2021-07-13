package xyz.subho.clone.twitter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "Mentions")
@Table(name = "mentions")
@Data
@RequiredArgsConstructor
public class Mentions implements Serializable {
	
	private static final long serialVersionUID = 18011079306L;
	
	@EmbeddedId private PostsUsersId id;
	
	@ManyToOne
	@MapsId("postsId")
	private Posts posts;
	
	@ManyToOne
	@MapsId("userId")
	private Users users;
	
	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean viewed = false;
	
	@CreationTimestamp private Date createdAt = new Date();

	  @UpdateTimestamp private Date updatedAt = new Date();
	  
	  public Mentions(Posts posts, Users users) {
		  this.id = new PostsUsersId(posts.getId(), users.getId());
		  this.posts = posts;
		  this.users = users;
		  this.viewed = false;
	  }
}
