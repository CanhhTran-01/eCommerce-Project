package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);
}
