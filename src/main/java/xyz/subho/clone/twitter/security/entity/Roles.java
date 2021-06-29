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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Entity(name = "Roles")
@Table(
    name = "roles",
    indexes = {@Index(columnList = "roles_name")})
@Data
public class Roles implements Serializable {

  private static final long serialVersionUID = 38920613133424876L;

  @Id
  @Column(name = "roles_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer rolesId;

  @Column(name = "roles_name", nullable = false, length = 30, unique = true)
  @NaturalId
  private String rolesName;

  @OneToMany(
      mappedBy = "roles",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  @JsonIgnore
  private List<UsersRoles> usersRoles = new ArrayList<>();

  /** @param rolesName */
  public Roles(String rolesName) {
    this.rolesName = rolesName;
  }
}
