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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.security.Authority;

@Entity
@Table(
    indexes = {@Index(columnList = "username"), @Index(columnList = "users_id")})
@Data
public class UsersAuthenticationDetails implements UserDetails, Serializable {

  private static final long serialVersionUID = 3577019301886090937L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private Users users;

  @OneToOne(fetch = FetchType.EAGER, targetEntity = Users.class)
  @JoinColumn(name = "username", nullable = false, unique = true, updatable = false)
  private String username;

  @Column(name = "password", length = 150)
  private String password;

  @Column(name = "salt")
  private String salt;

  @Column(name = "enabled", columnDefinition = "boolean default true", nullable = false)
  private Boolean enabled = true;

  @Column(name = "account_non_expired", columnDefinition = "boolean default true", nullable = false)
  private Boolean accountNonExpired = true;

  @Column(name = "account_non_locked", columnDefinition = "boolean default true", nullable = false)
  private Boolean accountNonLocked = true;

  @Column(
      name = "credentials_non_expired",
      columnDefinition = "boolean default true",
      nullable = false)
  private Boolean credentialsNonExpired = true;

  @OneToMany(
      mappedBy = "UsersAuthenticationDetails",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  @JsonIgnore
  private Set<UsersRoles> usersRoles = new HashSet<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    usersRoles.forEach(
        userRole -> grantedAuthorities.add(new Authority(userRole.getRoles().getRolesName())));
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
