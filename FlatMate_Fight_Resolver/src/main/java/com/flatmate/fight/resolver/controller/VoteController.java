package com.flatmate.fight.resolver.controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flatmate.fight.resolver.model.Complaint;
import com.flatmate.fight.resolver.service.ComplaintService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/complaints")
@Tag(name = "Voting", description = "APIs for voting on complaints")
public class VoteController {
	@Autowired
	private ComplaintService complaintService;
	
	private static final String[] PUNISHMENTS = {
	        "Didn’t clean the dishes? You’re making chai for everyone for a week.",
	        "Blasted loud music at 2 AM? You owe everyone samosas.",
	        "Left the lights on again? You’re paying the next electricity bill!",
	        "Hogged the TV all weekend? No remote control for you for a week!"
	    };
	
	@Operation(summary = "Vote on a complaint",
		    description = "Upvote or downvote a complaint. If upvotes reach 10+, a punishment is assigned.")
		@ApiResponses({
		    @ApiResponse(responseCode = "200", description = "Vote registered successfully",
		    content = @Content(schema = @Schema(example = "{ \"message\": \"Voting Done\", \"upvotes\": 10, \"downvotes\": 2, \"punishment\": \"Blasted loud music at 2 AM? You owe everyone samosas.\" }"))),
		    @ApiResponse(responseCode = "400", description = "Invalid vote type"),
		    @ApiResponse(responseCode = "404", description = "Complaint not found")
		})
	@PutMapping("/{id}/vote")
	public ResponseEntity<?> voteComplaint(@Parameter(description = "ID of the complaint to vote on", example = "1")@PathVariable Long id,
		@Parameter(description = "Vote type (upvote/downvote)", example = "upvote")@RequestParam String voteType){
		
		Optional<Complaint> complaint = complaintService.findById(id);
		if(complaint.isEmpty()) {
			return ResponseEntity.status(404).body("Complaint not found");
		}
		Complaint c = complaint.get();
		if("upvote".equalsIgnoreCase(voteType))
			c.setUpvote(c.getUpvote()+1);
		else if("downvote".equalsIgnoreCase(voteType))
			c.setDownvote(c.getDownvote()+1);
		else
			return ResponseEntity.badRequest().body("Invalid vote type");
		
		if (c.getUpvote() >= 10 && c.getPunishment() == null) {
            c.setPunishment(getRandomPunishment());
        }
		
		complaintService.createComplaint(c);
		return ResponseEntity.ok().body(
	            new VotingResponse("Voting Done", c.getUpvote(), c.getDownvote(), c.getPunishment())
	        );
	}
	
	private String getRandomPunishment() {
        Random rand = new Random();
        return PUNISHMENTS[rand.nextInt(PUNISHMENTS.length)];
    }
	
	private static class VotingResponse {
        public String message;
        public int upvotes;
        public int downvotes;
        public String punishment;

        public VotingResponse(String message, int upvotes, int downvotes, String punishment) {
            this.message = message;
            this.upvotes = upvotes;
            this.downvotes = downvotes;
            this.punishment = punishment;
        }
    }
}
