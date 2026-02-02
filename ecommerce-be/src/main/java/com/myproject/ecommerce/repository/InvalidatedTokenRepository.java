package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidToken, String> {
}
