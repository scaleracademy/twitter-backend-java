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

package xyz.subho.clone.twitter.utility.security;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;

public class RandomStringUtil {

  private static RandomStringUtil defaultInstacne = null;
  public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String LOWER;
  public static final String DIGITS = "0123456789";
  public static final String ALPHA_NUMERIC;
  private final SecureRandom random;
  private final char[] symbols;
  private final char[] buf;

  static {
    LOWER = UPPER.toLowerCase(Locale.ROOT);
    ALPHA_NUMERIC = UPPER + LOWER + DIGITS;
  }

  public static RandomStringUtil getInstance() {
    return defaultInstacne == null ? new RandomStringUtil() : defaultInstacne;
  }

  public RandomStringUtil() {
    this(32);
  }

  public RandomStringUtil(int length) {
    this(length, new SecureRandom());
  }

  private RandomStringUtil(int length, SecureRandom random) {
    this(length, random, ALPHA_NUMERIC);
  }

  private RandomStringUtil(int length, SecureRandom random, String symbols) {
    if (length < 1) {
      throw new IllegalArgumentException();
    } else if (symbols.length() < 2) {
      throw new IllegalArgumentException();
    } else {
      this.random = (SecureRandom) Objects.requireNonNull(random);
      this.symbols = symbols.toCharArray();
      this.buf = new char[length];
    }
  }

  public String nextString() {

    for (var idx = 0; idx < this.buf.length; ++idx) {
      this.buf[idx] = this.symbols[this.random.nextInt(this.symbols.length)];
    }
    return new String(this.buf);
  }
}
