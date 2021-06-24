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
