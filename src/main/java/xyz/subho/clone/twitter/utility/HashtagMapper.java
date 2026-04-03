/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2023 Subhrodip Mohanta (hello@subho.xyz)
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

package xyz.subho.clone.twitter.utility;

import org.springframework.stereotype.Component;
import xyz.subho.clone.twitter.entity.Hashtags;
import xyz.subho.clone.twitter.model.HashtagModel;

@Component("HashtagMapper")
public class HashtagMapper implements Mapper<Hashtags, HashtagModel> {

  @Override
  public HashtagModel transform(Hashtags hashtag) {
    return new HashtagModel(hashtag.getId(), hashtag.getTag(), hashtag.getRecentPostCount());
  }

  @Override
  public Hashtags transformBack(HashtagModel hashtagModel) {
    var hashtag = new Hashtags();
    hashtag.setId(hashtagModel.id());
    hashtag.setTag(hashtagModel.tag());
    hashtag.setRecentPostCount(
        hashtagModel.recentPostCount() != null ? hashtagModel.recentPostCount() : 0L);
    return hashtag;
  }
}
