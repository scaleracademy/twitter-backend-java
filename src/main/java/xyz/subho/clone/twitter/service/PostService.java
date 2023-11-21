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

package xyz.subho.clone.twitter.service;

import java.util.List;
import java.util.UUID;
import xyz.subho.clone.twitter.model.PostModel;

public interface PostService {

  public List<PostModel> getAllPosts();

  public PostModel getPost(UUID postId);

  public PostModel addPost(PostModel postModel);

  public boolean deletePost(UUID postId, UUID userId);

  public long addLike(UUID postId, UUID userId);

  public long removeLike(UUID postId, UUID userId);
}
