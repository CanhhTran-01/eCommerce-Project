package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByUsername(String username);
    Optional<AccountEntity> findByUsername(String username);
}
