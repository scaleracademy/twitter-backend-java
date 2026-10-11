/*
 * Twitter Backend - Moo: Twitter Clone Application Backend by Scaler
 * Copyright © 2021-2026 Subhrodip Mohanta (hello@subho.xyz)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */

package xyz.subho.clone.twitter.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Fields that an authenticated user may change on their own profile. */
public record UserUpdateRequest(
    @NotBlank @Size(max = 100) String name,
    @NotBlank @Email String email,
    @Size(max = 240) String avatar,
    @Size(max = 240) String bio,
    @Size(min = 8, max = 100) String password) {}
