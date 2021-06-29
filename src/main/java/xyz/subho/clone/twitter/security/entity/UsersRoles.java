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

package xyz.subho.clone.twitter.security.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity(name = "UsersRoles")
@Table(name = "users_roles")
@Data
public class UsersRoles implements Serializable {

  private static final long serialVersionUID = 6266028818011079306L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /*
  @ManyToOne(targetEntity = UsersAuthenticationDetails.class, fetch = FetchType.EAGER)
  @JoinColumn(
       name = "users_id",
       columnDefinition = "BINARY(16)",
       updatable = false,
       nullable = false)*/
  private UsersAuthenticationDetails usersAuthenticationEntity;

  @ManyToOne(targetEntity = Roles.class, fetch = FetchType.EAGER)
  @JoinColumn(name = "roles_id")
  private Roles roles;
}
