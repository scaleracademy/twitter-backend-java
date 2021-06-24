package xyz.subho.clone.twitter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xyz.subho.clone.twitter.entity.Hashtags;
import xyz.subho.clone.twitter.entity.Posts;
import xyz.subho.clone.twitter.model.HashtagModel;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.repository.HashtagPostsRepository;
import xyz.subho.clone.twitter.repository.HashtagsRepository;
import xyz.subho.clone.twitter.service.HashtagService;
import xyz.subho.clone.twitter.utility.Mapper;

@Service
public class HashtagServiceImpl implements HashtagService {

  @Autowired private HashtagsRepository hashtagsRepository;

  @Autowired private HashtagPostsRepository hashtagPostsRepository;

  @Autowired
  @Qualifier("HashtagMapper")
  private Mapper<Hashtags, HashtagModel> hashtagMapper;

  @Autowired
  @Qualifier("PostMapper")
  private Mapper<Posts, PostModel> postMapper;

  // TODO: Create a stored procedure in DB.
  @Override
  public List<HashtagModel> getHashtags() {
    var hashtags = hashtagsRepository.findAll();
    List<HashtagModel> hashtagModels = new ArrayList<>();
    Optional.ofNullable(hashtags)
        .ifPresent(
            hashtag -> {
              hashtag.forEach(hTag -> hashtagModels.add(hashtagMapper.transform(hTag)));
            });
    return hashtagModels;
  }

  @Override
  public List<PostModel> getPosts(String tag) {
    var hashtag = hashtagsRepository.findByTag(tag);
    List<Posts> posts = new ArrayList<>();
    if (null != hashtag) {
      posts = hashtagPostsRepository.findByHashtag(hashtag);
    }
    List<PostModel> postModels = new ArrayList<>();
    Optional.ofNullable(posts)
        .ifPresent(
            post -> {
              post.forEach(pst -> postModels.add(postMapper.transform(pst)));
            });
    return postModels;
  }
}
