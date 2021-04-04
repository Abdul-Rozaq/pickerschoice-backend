package com.pickerschoice.pickerschoice.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pickerschoice.pickerschoice.model.Customer;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Optional<Customer> findByEmail(String email);

	@Transactional
	@Modifying
	@Query("UPDATE Customer a SET a.enabled = TRUE WHERE a.email = ?1")
	int enableAppUser(String email);
}
