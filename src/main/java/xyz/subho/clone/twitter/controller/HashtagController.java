package xyz.subho.clone.twitter.controller;

import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import xyz.subho.clone.twitter.entity.Hashtag;
import xyz.subho.clone.twitter.model.HashtagModel;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.repository.HashtagsRepository;
import xyz.subho.clone.twitter.service.HashtagService;

@RestController
@Slf4j
public class HashtagController {

	@Autowired
  private HashtagService hashtagService;
  
	@Autowired
  HashtagsRepository hashtagsRepository;

  @GetMapping("/hashtags")
  public ResponseEntity<List<HashtagModel>> getAllHashtags() {
    List<HashtagModel> hashtags = hashtagService.getHashtags();
    return new ResponseEntity<>(hashtags, HttpStatus.OK);
  }

  @GetMapping("/hashtag/{tag}/posts")
  public ResponseEntity<List<PostModel>> getPosts(@PathVariable("tag") String tag) {
    List<PostModel> posts = hashtagService.getPosts(tag);
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }
  
  @GetMapping("/mh/{tag}")
  public boolean makeHashTag(@PathVariable("tag")String tag) {
	  Hashtag h = new Hashtag();
	  h.setCreatedAt(new Date());
	  h.setTag(tag);
	  h.setUpdatedAt(new Date());
	  hashtagsRepository.save(h);
	  return true;
  }
}
