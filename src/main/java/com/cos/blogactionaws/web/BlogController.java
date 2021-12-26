package com.cos.blogactionaws.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

	@GetMapping("/")
	public ResponseEntity<String> home() {
		return new ResponseEntity<>("<h1>Home</h1>", HttpStatus.OK);
	}
}
