package com.myproject.ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.ecommerce.dto.request.AuthenticationRequest;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.security.handler.OAuth2LoginSuccessHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AuthenticationControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @MockitoBean
    private StringRedisTemplate stringRedisTemplate;

    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;

    @MockitoBean
    private JavaMailSender javaMailSender;

    @MockitoBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @BeforeEach
    void setup() {
        Account account = Account.builder()
                .username("testLogin")
                .password(passwordEncoder.encode("testPass"))
                .accountRoles(Set.of(Role.USER))
                .build();
        accountRepository.save(account);
    }

    @Test
    void login_success_shouldReturnToken() throws Exception {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("testLogin")
                .password("testPass")
                .build();

        mockMvc.perform(
                        post("/api/auth/login") // mock login request
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(request))) // convert Java object -> json string
                .andExpect(status().isOk()) // expect status 200 OK
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.token").isNotEmpty()) // expect token
                .andExpect(jsonPath("$.data.authenticated").value(true));
    }

    @Test
    void login_wrongUsername_shouldReturnError() throws Exception {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username("notFound")
                .password("testPass")
                .build();

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
