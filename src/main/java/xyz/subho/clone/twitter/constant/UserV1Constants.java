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

package xyz.subho.clone.twitter.constant;

/** UserV1Constants - API endpoint constants for User V1 Controller. */
public class UserV1Constants {

  public static final String BASE_PATH = ApiVersion.V1 + "/users";
  public static final String USER_ID_OR_NAME = "/{userNameOrUserId}";
  public static final String FOLLOW = "/{userId}/follow";
  public static final String FOLLOWERS = "/{userId}/followers";
  public static final String FOLLOWINGS = "/{userId}/followings";

  private UserV1Constants() {
    // Prevent instantiation
  }
}
