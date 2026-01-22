package com.myproject.ecommerce.configuration;

import com.myproject.ecommerce.entity.AccountEntity;
import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@Slf4j
public class ApplicationInitConfig {

    @Bean
    ApplicationRunner applicationRunner (AccountRepository accountRepository,
                                         PasswordEncoder passwordEncoder){
        return args -> {
            if (accountRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<Role>();
                roles.add(Role.ADMIN);

                AccountEntity accountEntity = AccountEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin@123"))
                        .accountRoles(roles)
                        .build();

                accountRepository.save(accountEntity);
                log.info("Admin account created");
            }
        };
    }
}
