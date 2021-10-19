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

package xyz.subho.clone.twitter.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import xyz.subho.clone.twitter.model.ErrorResponse;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> globalExceptionHandler(
      Exception exception, WebRequest webRequest) {
    return getErrorResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST,
        webRequest.getDescription(false));
  }

  @ExceptionHandler(value = {BadRequestException.class})
  public ResponseEntity<ErrorResponse> handleBadRequestException(
      BadRequestException bindRequestException, WebRequest webRequest) {

    return getErrorResponseEntity(bindRequestException.getMessage(), HttpStatus.BAD_REQUEST,
        webRequest.getDescription(false));
  }

  @ExceptionHandler(value = {ErrorSavingEntityToDatabaseException.class})
  public ResponseEntity<ErrorResponse> handleErrorSavingEntityToDatabaseException(
      ErrorSavingEntityToDatabaseException errorSavingEntityToDatabaseException,
      WebRequest webRequest) {

    return getErrorResponseEntity(errorSavingEntityToDatabaseException.getMessage(),
        HttpStatus.UNPROCESSABLE_ENTITY,
        webRequest.getDescription(false));
  }

  @ExceptionHandler(value = {ResourceNotFoundException.class})
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException resourceNotFoundException, WebRequest webRequest) {

    return getErrorResponseEntity(resourceNotFoundException.getMessage(),
        HttpStatus.NOT_FOUND,
        webRequest.getDescription(false));
  }

  private ResponseEntity<ErrorResponse> getErrorResponseEntity(String message,
      HttpStatus httpStatus, String description) {
    var errorResponse = new ErrorResponse(
        httpStatus.value(),
        new Date(System.currentTimeMillis()),
        message,
        description);
    return new ResponseEntity<>(errorResponse, httpStatus);
  }

}
