package xyz.subho.clone.twitter.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import xyz.subho.clone.twitter.entity.Hashtag;
import xyz.subho.clone.twitter.model.HashtagModel;

@Component("HashtagMapper")
public class HashtagMapper implements Mapper<Hashtag, HashtagModel> {

  @Override
  public HashtagModel transform(Hashtag hashtag) {
    var hashtagModel = new HashtagModel();
    BeanUtils.copyProperties(hashtag, hashtagModel);
    return hashtagModel;
  }

  @Override
  public Hashtag transformback(HashtagModel hashtagModel) {
    var hashtag = new Hashtag();
    BeanUtils.copyProperties(hashtagModel, hashtag);
    return hashtag;
  }
}
