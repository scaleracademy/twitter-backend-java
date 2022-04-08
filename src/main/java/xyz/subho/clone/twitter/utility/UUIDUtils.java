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

import java.util.UUID;

public class UUIDUtils {

  private UUIDUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * @param String uuid
   * @return TRUE or FALSE if the given String is a valid UUID
   */
  public static boolean isUUID(String uuid) {

    try {
      UUID.fromString(uuid);
      return true;
    } catch (Exception exp) {
      return false;
    }
  }

  /**
   * @param String uuid
   * @return UUID uuid or NULL if invalid
   */
  public static UUID converStringToUUID(String uuid) {
    return isUUID(uuid) ? UUID.fromString(uuid) : null;
  }
}
