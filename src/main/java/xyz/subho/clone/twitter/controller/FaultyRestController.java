package xyz.subho.clone.twitter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaultyRestController {
	
	@GetMapping("/exception")
	public ResponseEntity<String> makeRequestToInvokeException ()	{
		throw new RuntimeException("Hola! From the FaultyRestController");
	}

}
