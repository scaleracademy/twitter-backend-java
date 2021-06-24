package xyz.subho.clone.twitter.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import xyz.subho.clone.twitter.entity.Hashtags;
import xyz.subho.clone.twitter.model.HashtagModel;

@Component("HashtagMapper")
public class HashtagMapper implements Mapper<Hashtags, HashtagModel> {

  @Override
  public HashtagModel transform(Hashtags hashtag) {
    var hashtagModel = new HashtagModel();
    BeanUtils.copyProperties(hashtag, hashtagModel);
    return hashtagModel;
  }

  @Override
  public Hashtags transformback(HashtagModel hashtagModel) {
    var hashtag = new Hashtags();
    BeanUtils.copyProperties(hashtagModel, hashtag);
    return hashtag;
  }
}
