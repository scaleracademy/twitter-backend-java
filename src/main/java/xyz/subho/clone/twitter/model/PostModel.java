package xyz.subho.clone.twitter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import xyz.subho.clone.twitter.entity.User;

@Data
public class PostModel {

  private UUID id;
  private String text;
  private User user;
  private Long likeCount;
  private Long repostCount;
  private UUID originalPostId;
  private UUID replyToId;
  private Date timestamp;
  private Map<UUID, String> hashtags = new HashMap<>();
  private Map<UUID, String> mentions = new HashMap<>();
  private List<HashtagPostModel> postHashtags = new ArrayList<>();
  private List<LikeModel> postLikes = new ArrayList<>();
}
