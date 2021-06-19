package xyz.subho.clone.twitter.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.subho.clone.twitter.model.HashtagModel;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.service.HashtagService;

@RestController
@Slf4j
public class HashtagController {

  private HashtagService hashtagService;

  @GetMapping("/hashtags")
  public ResponseEntity<List<HashtagModel>> getAllHashtags() {
    List<HashtagModel> hashtags = hashtagService.getHashtags();
    return new ResponseEntity<>(hashtags, HttpStatus.OK);
  }

  @GetMapping("/hashtag/{tag}/posts")
  public ResponseEntity<List<PostModel>> getPosts() {
    List<PostModel> posts = hashtagService.getPosts();
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }
}
