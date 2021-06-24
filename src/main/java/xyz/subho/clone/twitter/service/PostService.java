package xyz.subho.clone.twitter.service;

import java.util.List;
import xyz.subho.clone.twitter.model.PostModel;

public interface PostService {

  public List<PostModel> getAllPosts();

  public PostModel getPost();

  public PostModel addPost();

  public boolean deletePost();

  public boolean addLike();

  public boolean removeLike();
}
