package xyz.subho.clone.twitter.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPostsId implements Serializable {

  private static final long serialVersionUID = 46137789784325L;

  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID userId;

  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID postId;
}
