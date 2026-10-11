/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright Â© 2021-2026 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package xyz.subho.clone.twitter.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

class ControllerExceptionHandlerTest {

  private final ControllerExceptionHandler handler = new ControllerExceptionHandler();
  private final WebRequest request = mock(WebRequest.class);

  @Test
  void malformedRequestsReturnBadRequestWithoutInternalDetails() {
    when(request.getDescription(false)).thenReturn("uri=/v1/posts");

    var response = handler.malformedRequestExceptionHandler(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().message()).isEqualTo("Malformed request");
    assertThat(response.getBody().description()).isEqualTo("uri=/v1/posts");
  }

  @Test
  void duplicateDataReturnsConflictWithoutDatabaseDetails() {
    when(request.getDescription(false)).thenReturn("uri=/v1/users");

    var response = handler.dataIntegrityExceptionHandler(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    assertThat(response.getBody().message()).isEqualTo("Request conflicts with existing data");
  }
}
