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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flatmate.fight.resolver.model.Complaint;
import com.flatmate.fight.resolver.model.User;
import com.flatmate.fight.resolver.repository.UserRepository;
import com.flatmate.fight.resolver.service.ComplaintService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/complaints")
@Tag(name = "Complaints", description = "Complaint Management APIs")
public class ComplaintController {
	@Autowired
	private ComplaintService complaintService;
	@Autowired
	private UserRepository userRepository;
	
	@Operation(summary = "Create a complaint", description = "Registers a complaint for a flat.")
	@PostMapping
	public ResponseEntity<?> createComplaint(@RequestBody Complaint complaint){
		String username = getLoggedInUsername();
		if (username == null) {
            return ResponseEntity.status(403).body("User is not authenticated");
        }
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isEmpty()) {
			return ResponseEntity.status(404).body("User not found");
		}
		
		User userObj = user.get();
		complaint.setUser(userObj);
		complaint.setFlatCode(userObj.getFlatCode());
		
		Complaint savedComplaint = complaintService.createComplaint(complaint);
		return ResponseEntity.ok(savedComplaint);
	}
	
	@Operation(summary = "Get all complaints", description = "Fetches all complaints in the system.")
	@GetMapping
	public ResponseEntity<List<Complaint>> getAllComplaints(){
		return ResponseEntity.ok(complaintService.getAllComplaints());
	}
	
	@Operation(summary = "Get complaints by flat code", description = "Fetches all complaints for a specific flat.")
	@GetMapping("/flat/{flatcode}")
	public ResponseEntity<?> getComplaintsByFlat(@Parameter(description = "Flat code to fetch complaints") @PathVariable String flatcode){
		return ResponseEntity.ok(complaintService.getComplaintsByFlat(flatcode));
	}
	
	@Operation(summary = "Resolve a complaint", description = "Marks a complaint as resolved and updates karma points.")
	@PutMapping("/{id}/resolve")
	public ResponseEntity<?> resolveComplaint(@Parameter(description = "Complaint ID to be resolved") @PathVariable Long id){
		String username = getLoggedInUsername();
		Optional<Complaint> complaint = complaintService.findById(id);
		if(complaint.isEmpty()) {
			return ResponseEntity.status(404).body("Complaint not found");
		}
		
		Complaint c = complaint.get();
		if(c.isResolved()) {
			return ResponseEntity.ok("Complaint is already resolved");
		}
		
		c.setResolved(true);
		complaintService.createComplaint(c);
		
		Optional<User> resolveuser = userRepository.findByUsername(username); 
		if(resolveuser.isPresent()) {
			User resolver = resolveuser.get();
			resolver.setKarmaPoints(resolver.getKarmaPoints()+10);
			userRepository.save(resolver);
		}
		
		return ResponseEntity.ok("Complaint marked as resolved");
	}
	
	private String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return null;
    }
	
	@Operation(summary = "Get trending complaints", description = "Retrieves the top 5 most upvoted complaints.")
	@GetMapping("/trending")
	public ResponseEntity<List<Complaint>> getTrendingComplaints(){
		List<Complaint> trendingComplaints = complaintService.getTrendingComplaints();
		if(trendingComplaints.isEmpty()) {
			return ResponseEntity.status(404).body(null);
		}
		return ResponseEntity.ok(trendingComplaints);
	}
}
