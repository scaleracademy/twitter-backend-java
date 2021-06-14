package xyz.subho.clone.twitter.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.NotFound;

import lombok.extern.slf4j.Slf4j;
import xyz.subho.clone.twitter.responseModel.UserResponse;
import xyz.subho.clone.twitter.service.UserService;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
	
	//@Autowired
	private UserService userService;
	
	@GetMapping("/{userId}")
	ResponseEntity<String> getUserByUserIdOrUserName(String userId)	{
		
		try	{		
			
			//if (userIdOrUserName.equals("^@"))
				//return new ResponseEntity<>(new UserResponse(userService.getUserByUserName(userId)), HttpStatus.OK);
			
			return ResponseEntity.ok(userId);
		}
		catch (NotFound notFound)	{
			log.warn(notFound.getMessage());
			return ResponseEntity.notFound().build();
		}
		catch (Exception exception)	{
			log.error(exception.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

}
