package xyz.subho.clone.twitter.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import xyz.subho.clone.twitter.entity.Posts;
import xyz.subho.clone.twitter.model.PostModel;

@Component("PostMapper")
public class PostMapper implements Mapper<Posts, PostModel> {

  @Override
  public PostModel transform(Posts post) {
    PostModel postModel = new PostModel();
    BeanUtils.copyProperties(post, postModel);
    postModel.setUserId(post.getUser().getId());
    return postModel;
  }

  @Override
  public Posts transformback(PostModel postModel) {
    Posts post = new Posts();
    BeanUtils.copyProperties(postModel, post);
    return post;
  }
}
