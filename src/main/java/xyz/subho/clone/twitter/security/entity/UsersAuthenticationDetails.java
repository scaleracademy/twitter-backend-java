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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.subho.clone.twitter.entity.Users;
import xyz.subho.clone.twitter.security.Authority;

@Entity(name = "UsersAuthenticationDetails")
@Table(
    name = "users_authentication",
    indexes = {@Index(columnList = "username")})
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
public class UsersAuthenticationDetails implements UserDetails, Serializable {

  private static final long serialVersionUID = 3577019301886090937L;

  @Id
  @Column(columnDefinition = "BINARY(16)")
  private UUID id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(columnDefinition = "BINARY(16)")
  private Users users;

  @OneToOne(fetch = FetchType.EAGER, targetEntity = Users.class)
  @JoinColumn(name = "username", nullable = false, unique = true, updatable = false)
  private String username;

  @Column(name = "password", length = 150, nullable = false)
  private String password;

  @Column(name = "salt", nullable = false)
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
      mappedBy = "usersAuthentication",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER)
  @JsonIgnore
  private List<UsersRoles> usersRoles = new ArrayList<>();

  private Date createdAt;

  private Date updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = new Date();
  }

  public boolean assignRole(Roles roles) {
    var userRoles = new UsersRoles(this, roles);
    this.usersRoles.add(userRoles);
    return roles.getUsersRoles().add(userRoles);
  }

  public void unasignRole(Roles role) {
    List<UsersRoles> toBeDeleted =
        usersRoles.stream()
            .filter(
                userRole ->
                    userRole.getRoles().equals(role)
                        && userRole.getUsersAuthentication().equals(this))
            .collect(Collectors.toList());
    usersRoles.removeAll(toBeDeleted);
  }

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
