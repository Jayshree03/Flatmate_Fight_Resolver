package com.flatmate.fight.resolver.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flatmate.fight.resolver.model.User;
import com.flatmate.fight.resolver.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/flatmates")
@Tag(name = "Flatmates", description = "APIs for managing flatmates")
public class FlatmateController {
	@Autowired
	private UserRepository userRepository;
	
	@Operation(summary = "Assign user to a flat", description = "Assigns the logged-in user to a flat.")
	@PostMapping("/assign")
	public ResponseEntity<?> assignToFlat(@Parameter(description = "Flat code to assign user") @RequestParam String flatCode){
		String username = getLoggedUsername();
		if(username == null) {
			return ResponseEntity.status(404).body("user is not authenticated");
		}
		
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isEmpty()) {
			return ResponseEntity.status(404).body("User not found");
		}
		
		List<User> flatmates = userRepository.findByFlatCode(flatCode);
		if(flatmates.size()>=4) {
			return ResponseEntity.status(400).body("Flat is full maximum 4 members are allowed");
		}
		
		User userObj = user.get();
		userObj.setFlatCode(flatCode);
		userRepository.save(userObj);
		
		return ResponseEntity.ok("User is assigned to flat: "+flatCode);
	}
	
	@Operation(summary = "Get all members of a flat", description = "Fetches the list of flatmates assigned to a given flat.")
	@GetMapping("/{flatCode}/members")
	public ResponseEntity<?> getFlatmates(@Parameter(description = "Flat code to fetch members") @PathVariable String flatCode){
		 List<User> flatmates = userRepository.findByFlatCode(flatCode);

		    if (flatmates.isEmpty()) {
		        return ResponseEntity.status(404).body("No users found in this flat.");
		    }

		    return ResponseEntity.ok(flatmates);
	}
	
	@Operation(summary = "Get leaderboard", description = "Fetches the top 5 users with the highest karma points.")
	@GetMapping("/leaderboard")
	public ResponseEntity<?> getLeaderboard(){
		List<User> leaderboard = userRepository.findTop5ByOrderByKarmaPointsDesc();
		if(leaderboard.isEmpty()) {
			return ResponseEntity.status(404).body("No users found in the leaderboard");
		}
		return ResponseEntity.ok(leaderboard);
	}
	
	private String getLoggedUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			return ((UserDetails)principal).getUsername();
		}
		return null;
	}
	
}
