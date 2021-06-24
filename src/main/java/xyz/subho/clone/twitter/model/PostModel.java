package xyz.subho.clone.twitter.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Data;

@Data
public class PostModel {

  private UUID id;
  private String text;
  private UUID userId;
  private Long likeCount;
  private Long repostCount;
  private UUID originalPostId;
  private UUID replyToId;
  private Date timestamp;
  private Map<UUID, Date> hashtags = new HashMap<>();
  private Map<UUID, Date> mentions = new HashMap<>();
}
