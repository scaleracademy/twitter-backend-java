package xyz.subho.clone.twitter.security;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(targetEntity = UsersAuthenticationEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "users_id")
	private UsersAuthenticationEntity usersAuthenticationEntity;
	
	@ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)	
	@JoinColumn(name = "role_id")
	private Role role;

}
