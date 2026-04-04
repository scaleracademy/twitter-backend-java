/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2026 Subhrodip Mohanta (hello@subho.xyz)
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import xyz.subho.clone.twitter.entity.Posts;
import xyz.subho.clone.twitter.model.PostModel;

@Mapper(componentModel = "spring")
public interface PostMapper {

  @Mapping(target = "userId", source = "users.id")
  @Mapping(target = "hashtags", source = "hashtags", qualifiedByName = "mapToKeysList")
  @Mapping(target = "mentions", source = "mentions", qualifiedByName = "mapToKeysList")
  @Mapping(target = "images", source = "images", qualifiedByName = "mapToKeysList")
  @Mapping(target = "timestamp", source = "createdAt")
  PostModel toModel(Posts post);

  @Mapping(target = "users", ignore = true)
  @Mapping(target = "postHashtags", ignore = true)
  @Mapping(target = "postLikes", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "hashtags", source = "hashtags", qualifiedByName = "listToDateMap")
  @Mapping(target = "mentions", source = "mentions", qualifiedByName = "listToDateMap")
  @Mapping(target = "images", source = "images", qualifiedByName = "listToDateMap")
  Posts toEntity(PostModel postModel);

  @Named("mapToKeysList")
  default List<String> mapToKeysList(Map<String, Date> map) {
    if (map == null) return new ArrayList<>();
    return new ArrayList<>(map.keySet());
  }

  @Named("listToDateMap")
  default Map<String, Date> listToDateMap(List<String> list) {
    Map<String, Date> map = new HashMap<>();
    if (list != null) {
      list.forEach(item -> map.put(item, new Date()));
    }
    return map;
  }
}
