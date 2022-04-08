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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

  private IOUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static void closeQuietly(InputStream inputStream) throws IOException {
    if (inputStream != null) {
      inputStream.close();
    }
  }

  public static void closeQuietly(OutputStream outputStream) throws IOException {
    if (outputStream != null) {
      outputStream.close();
    }
  }
}
