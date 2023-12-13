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

package xyz.subho.clone.twitter.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class ApplicationErrorController extends BasicErrorController {

  /**
   * @param errorAttributes ErrorAttributes
   * @param serverProperties ServerProperties
   */
  public ApplicationErrorController(
      ErrorAttributes errorAttributes, ServerProperties serverProperties) {
    super(errorAttributes, serverProperties.getError());
  }

  /**
   * @param request HttpServletRequest
   * @return ResponseEntity for Errors
   */
  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> jsonError(HttpServletRequest request) {

    Map<String, Object> body =
        getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.APPLICATION_JSON));

    body.put("jsonkey", "New JSON");
    HttpStatus status = getStatus(request);

    return new ResponseEntity<>(body, status);
  }
}
