/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2026 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package xyz.subho.clone.twitter.utility;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import xyz.subho.clone.twitter.entity.Posts;
import xyz.subho.clone.twitter.model.PostModel;

class UtilityAndMapperTest {

  @Test
  void utilityAcceptsOnlyUuidStrings() {
    Utility utility = new Utility();
    UUID id = UUID.randomUUID();

    assertThat(utility.isUUID(id.toString())).isTrue();
    assertThat(utility.isUUID("bad")).isFalse();
    assertThat(utility.converStringToUUID(id.toString())).isEqualTo(id);
    assertThat(utility.converStringToUUID("bad")).isNull();
  }

  @Test
  void postMapperHandlesNullAndNonNullCollections() {
    PostMapper mapper = Mappers.getMapper(PostMapper.class);
    assertThat(mapper.mapToKeysList(null)).isEmpty();
    assertThat(mapper.mapToKeysList(Map.of("java", new Date()))).containsExactly("java");
    assertThat(mapper.listToDateMap(null)).isEmpty();
    assertThat(mapper.listToDateMap(List.of("java"))).containsKey("java");
  }

  @Test
  void generatedPostMappingDoesNotTrustClientIdentityOrCounters() {
    PostMapper mapper = Mappers.getMapper(PostMapper.class);
    Posts post =
        mapper.toEntity(
            new PostModel(
                UUID.randomUUID(),
                "text",
                UUID.randomUUID(),
                List.of(),
                99L,
                88L,
                null,
                null,
                null,
                null,
                null));

    assertThat(post.getId()).isNull();
    assertThat(post.getLikeCount()).isZero();
    assertThat(post.getRepostCount()).isZero();
  }
}
