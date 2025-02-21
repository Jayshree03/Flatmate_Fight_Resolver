package com.flatmate.fight.resolver.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flatmate.fight.resolver.model.LoginRequest;
import com.flatmate.fight.resolver.model.User;
import com.flatmate.fight.resolver.repository.UserRepository;
import com.flatmate.fight.resolver.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/auth")
@Tag(name="Authentication", description="User Authentication APIs")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Operation(summary = "Register a new user", description = "Registers a user with username, password, email, and flatCode.")
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user){
		if (user.getFlatCode() == null || user.getFlatCode().isEmpty()) {
            return ResponseEntity.badRequest().body("FlatCode cannot be null or empty");
        }
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

		List<User> flatmates = userRepository.findByFlatCode(user.getFlatCode());
		if(flatmates.size()>=4) {
			return ResponseEntity.status(400).body("Flat is full maximum 4 members are allowed");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return ResponseEntity.ok("User Registered successfully");
	}
	
	@Operation(summary = "User login", description = "Authenticates a user and returns a JWT token.")
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody(description = "Login Credentials", required = true, 
            content = @Content(schema = @Schema(implementation = LoginRequest.class))) 
        @org.springframework.web.bind.annotation.RequestBody LoginRequest loginRequest){
		Optional<User> existingUser = userRepository.findByUsername(loginRequest.getUsername());
		if(existingUser.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), existingUser.get().getPassword())) {
			String token = jwtUtil.generateToken(loginRequest.getUsername());
			return ResponseEntity.ok(token);
		}
		return ResponseEntity.status(401).body("Invalid username or password");
	}
}
