package xyz.subho.clone.twitter.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import xyz.subho.clone.twitter.entity.Users;

@Entity
@Data
@Table(name = "users_authentication", indexes = {@Index(columnList = "username,users_id")})
public class UsersAuthenticationEntity implements UserDetails{

	private static final long serialVersionUID = 3577019301886090937L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(columnDefinition = "BINARY(16)")
	private UUID id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	  private Users users;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = Users.class)
    @MapsId
	private String username;
	
	private String password;
	
	private String role;
	
	private Boolean enabled;
	
	private Boolean accountNonExpired;
	
	private Boolean accountNonLocked;
	
	private Boolean credentialsNonExpired;
	
	@OneToMany(mappedBy = "usersAuthenticationEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	  @JsonIgnore
	private Set<UserRole> userRoles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		userRoles.forEach(userRole -> {
			grantedAuthorities.add(new Authority(userRole.getRole().getRoleName()));
		});
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
