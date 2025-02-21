package com.flatmate.fight.resolver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flatmate.fight.resolver.model.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long>{
	List<Complaint> findByOrderByUpvoteDesc();
	List<Complaint> findByFlatCode(String flatCode);
	List<Complaint> findTop5ByOrderByUpvoteDesc();
}
