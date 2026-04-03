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

package xyz.subho.clone.twitter.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.jspecify.annotations.Nullable;

@Data
public class PostModel {

  private @Nullable UUID id;

  @NotBlank(message = "Post text cannot be empty")
  @Size(max = 240, message = "Post text cannot exceed 240 characters")
  private String text;

  private @Nullable UUID userId;
  private List<String> images = new ArrayList<>(4);
  private @Nullable Long likeCount;
  private @Nullable Long repostCount;
  private @Nullable UUID originalPostId;
  private @Nullable UUID replyToId;
  private @Nullable Date timestamp;
  private List<String> hashtags = new ArrayList<>();
  private List<String> mentions = new ArrayList<>();
}
