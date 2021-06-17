package xyz.subho.clone.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import xyz.subho.clone.twitter.model.UserResponse;
import xyz.subho.clone.twitter.service.UserService;
import xyz.subho.clone.twitter.utility.Utility;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	
	//@Autowired
	private UserService userService;
	
	@Autowired
	private Utility utility;
	
	@GetMapping("/{userNameOrUserId}")
	ResponseEntity<UserResponse> getUserByUserIdOrUserName(
			@PathVariable("userNameOrUserId") String userNameOrUserId)	{
		
		UserResponse userResponse;
		
		if(utility.isUUID(userNameOrUserId))	{
			var uuid = utility.converStringToUUID(userNameOrUserId);
			userResponse = new UserResponse(userService.getUserByUserId(uuid));
			return new ResponseEntity<>(userResponse, HttpStatus.OK);
		}
		else {
			var username = userNameOrUserId.startsWith("@") ? userNameOrUserId.substring(1) : userNameOrUserId;
			userResponse = new UserResponse(userService.getUserByUserName(username));
			return new ResponseEntity<>(userResponse, HttpStatus.OK);
		}
	}
	
	//ResponseEntity<Boolean> makeNewUser	()

}
