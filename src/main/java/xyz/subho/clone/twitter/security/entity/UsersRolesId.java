package xyz.subho.clone.twitter.security.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class UsersRolesId implements Serializable {
	
	private static final long serialVersionUID = 4729661377897825L;

	@Column(name = "users_id", columnDefinition = "BINARY(16)")
	private UUID usersId;
	
	@Column(name = "roles_id")
	private Integer rolesId;

}
