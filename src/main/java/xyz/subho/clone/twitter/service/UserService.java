package xyz.subho.clone.twitter.service;

import xyz.subho.clone.twitter.entity.Users;

public interface UserService {
	
	public Users getUserByUserName(String username);
	
	public Users getUserByUserId(String userId);

}
