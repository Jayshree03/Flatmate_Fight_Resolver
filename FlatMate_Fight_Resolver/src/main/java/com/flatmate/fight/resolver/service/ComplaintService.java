package com.flatmate.fight.resolver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flatmate.fight.resolver.model.Complaint;
import com.flatmate.fight.resolver.repository.ComplaintRepository;

@Service
public class ComplaintService {
	@Autowired
	private ComplaintRepository complaintRepository;
	public Complaint createComplaint(Complaint complaint) {
		return complaintRepository.save(complaint);
	}
	public List<Complaint> getAllComplaints(){
		return complaintRepository.findByOrderByUpvoteDesc();
	}
	public Optional<Complaint> findById(Long id){
		return complaintRepository.findById(id);
	}
	public void deleteComplaint(Long id) {
		complaintRepository.deleteById(id);
	}
	public List<Complaint> getComplaintsByFlat(String flatCode) {
        return complaintRepository.findByFlatCode(flatCode);
    }
	public List<Complaint> getTrendingComplaints() {
        return complaintRepository.findTop5ByOrderByUpvoteDesc();
    }
}
