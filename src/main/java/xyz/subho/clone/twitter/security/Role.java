package xyz.subho.clone.twitter.security;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Role {
	
	@Id
	private Integer roleId;
	
	@Column(nullable = false, length = 20, unique = true)
	private String roleName;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	  @JsonIgnore
	private Set<UserRole> userRoles;

}
