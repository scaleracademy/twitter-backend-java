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

import java.util.Date;

public class ErrorResponse {

  private Integer statusCode;
  private Date timestamp;
  private String message;
  private String description;

  /**
   * @param statusCode
   * @param timestamp
   * @param message
   * @param description
   */
  public ErrorResponse(Integer statusCode, Date timestamp, String message, String description) {
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.message = message;
    this.description = description;
  }

  /**
   * @return the statusCode
   */
  public Integer getStatusCode() {
    return statusCode;
  }

  /**
   * @return the timestamp
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return "ErrorResponse ["
        + (statusCode != null ? "statusCode=" + statusCode + ", " : "")
        + (timestamp != null ? "timestamp=" + timestamp + ", " : "")
        + (message != null ? "message=" + message + ", " : "")
        + (description != null ? "description=" + description : "")
        + "]";
  }
}
