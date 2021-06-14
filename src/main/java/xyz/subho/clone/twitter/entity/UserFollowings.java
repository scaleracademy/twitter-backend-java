package xyz.subho.clone.twitter.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_followings")
public class UserFollowings {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
		
	@OneToOne
	private Users followee;

	@OneToOne
	private Users follower;

}
