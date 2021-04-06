package com.pickerschoice.pickerschoice.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pickerschoice.pickerschoice.model.ConfirmationToken;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {
	Optional<ConfirmationToken> findByToken(String token);
	
	@Transactional
	@Modifying
	@Query("UPDATE ConfirmationToken a SET a.confirmedAt = ?2 WHERE a.token = ?1")
	int updateConfirmation(String token, LocalDateTime confirmedAt);
}
