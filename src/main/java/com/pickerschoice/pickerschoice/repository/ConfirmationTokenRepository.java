package com.pickerschoice.pickerschoice.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pickerschoice.pickerschoice.model.ConfirmationToken;

@Repository
@Transactional
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer> {
	Optional<ConfirmationToken> findByToken(String token);
}
