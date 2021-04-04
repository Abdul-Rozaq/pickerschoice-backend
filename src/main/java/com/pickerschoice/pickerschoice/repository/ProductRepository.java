package com.pickerschoice.pickerschoice.repository;

import com.pickerschoice.pickerschoice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findById(Integer productId);
}
