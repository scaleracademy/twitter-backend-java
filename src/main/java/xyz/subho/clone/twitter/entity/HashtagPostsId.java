package xyz.subho.clone.twitter.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class HashtagPostsId implements Serializable {

  private static final long serialVersionUID = 459689577736140L;

  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID hashtagId;

  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID postId;
}
