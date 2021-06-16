package xyz.subho.clone.twitter.service;

import java.util.UUID;

import xyz.subho.clone.twitter.entity.Users;

public interface UserService {
	
	public Users getUserByUserName(String username);
	
	public Users getUserByUserId(UUID userId);

}
