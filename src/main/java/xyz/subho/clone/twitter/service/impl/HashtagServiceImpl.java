package xyz.subho.clone.twitter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import xyz.subho.clone.twitter.entity.Hashtag;
import xyz.subho.clone.twitter.entity.Post;
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
  private Mapper<Hashtag, HashtagModel> hashtagMapper;

  @Autowired
  @Qualifier("PostMapper")
  private Mapper<Post, PostModel> postMapper;

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
    List<Post> posts = new ArrayList<>();
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
