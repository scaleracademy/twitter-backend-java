/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
