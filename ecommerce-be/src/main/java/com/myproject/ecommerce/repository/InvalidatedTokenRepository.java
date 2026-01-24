package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.InvalidTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidTokenEntity, String> {
}
