package xyz.subho.clone.twitter.model;

import java.util.UUID;
import lombok.Data;

@Data
public class HashtagPostModel {

  private UUID id;
  private HashtagModel hashtag;
  private PostModel post;
}
