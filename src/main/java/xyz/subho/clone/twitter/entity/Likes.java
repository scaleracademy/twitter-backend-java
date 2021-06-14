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
@Table(name = "likes")
public class Likes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "posts_id")
	private Posts posts;
	
	@ManyToOne(targetEntity = Users.class)
	@JoinColumn(name = "users_id")
	private Users users;

	/**
	 * @param id
	 * @param posts
	 * @param users
	 */
	public Likes(UUID id, Posts posts, Users users) {
		
		this.id = id;
		this.posts = posts;
		this.users = users;
	}
	
	/**
	 * @param posts
	 * @param users
	 */
	public Likes(Posts posts, Users users) {
		
		this.posts = posts;
		this.users = users;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, posts, users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Likes))
			return false;
		Likes other = (Likes) obj;
		return Objects.equals(id, other.id) && Objects.equals(posts, other.posts) && Objects.equals(users, other.users);
	}

	@Override
	public String toString() {
		return "Likes [id=" + id + ", posts=" + posts + ", users=" + users + "]";
	}

}
