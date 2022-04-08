package xyz.subho.clone.twitter.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
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

  private Date createdAt;

  private Date updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = new Date();
  }

  public Mentions(Posts posts, Users users) {
    this.id = new PostsUsersId(posts.getId(), users.getId());
    this.posts = posts;
    this.users = users;
    this.viewed = false;
  }
}
