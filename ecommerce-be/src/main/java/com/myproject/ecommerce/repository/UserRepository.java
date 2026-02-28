package com.myproject.ecommerce.repository;

import com.myproject.ecommerce.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // take user info from account (user belongs to that account)
    @Query("""
			SELECT a.user
			FROM Account a
			WHERE a.id = :accountId
			""")
    Optional<User> findByAccountId(@Param("accountId") Long accountId);
}
