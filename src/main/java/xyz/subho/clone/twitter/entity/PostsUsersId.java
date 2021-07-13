package xyz.subho.clone.twitter.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PostsUsersId implements Serializable{
	
	private static final long serialVersionUID = 6137925L;
	
	@Column(name = "id", columnDefinition = "BINARY(16)")
	  private UUID postsId;
	
	@Column(name = "id", columnDefinition = "BINARY(16)")
	  private UUID usersId;

}
