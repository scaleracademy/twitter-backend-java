package xyz.subho.clone.twitter.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import xyz.subho.clone.twitter.entity.Post;
import xyz.subho.clone.twitter.model.PostModel;

@Component("PostMapper")
public class PostMapper implements Mapper<Post, PostModel> {

  @Override
  public PostModel transform(Post post) {
    PostModel postModel = new PostModel();
    BeanUtils.copyProperties(post, postModel);
    postModel.setUserId(post.getUser().getId());
    return postModel;
  }

  @Override
  public Post transformback(PostModel postModel) {
    Post post = new Post();
    BeanUtils.copyProperties(postModel, post);
    return post;
  }
}
