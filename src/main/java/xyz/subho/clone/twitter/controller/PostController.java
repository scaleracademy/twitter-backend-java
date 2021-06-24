package xyz.subho.clone.twitter.controller;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.service.PostService;

@RestController
@RequestMapping("/post")
@Slf4j
public class PostController {

  private PostService postService;

  @GetMapping
  public ResponseEntity<List<PostModel>> getAllPosts() {
    List<PostModel> posts = postService.getAllPosts();
    return new ResponseEntity<>(posts, HttpStatus.OK);
  }

  @GetMapping("/{postid}")
  public ResponseEntity<PostModel> getPost() {
    PostModel post = postService.getPost();
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PostModel> addPost() {
    PostModel post = postService.addPost();
    return new ResponseEntity<>(post, HttpStatus.OK);
  }

  @DeleteMapping("/{postid}")
  public ResponseEntity<HttpStatus> deletePost() {
    postService.deletePost();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping("/{postid}/like")
  public ResponseEntity<HttpStatus> likePost() {
    postService.addLike();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{postid}/like")
  public ResponseEntity<HttpStatus> removeLikePost() {
    postService.removeLike();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
