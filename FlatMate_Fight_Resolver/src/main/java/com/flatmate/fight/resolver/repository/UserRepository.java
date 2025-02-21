package com.flatmate.fight.resolver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flatmate.fight.resolver.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	List<User> findByFlatCode(String flatCode);
	List<User> findTop5ByOrderByKarmaPointsDesc();
}
