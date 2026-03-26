package com.myproject.ecommerce.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.ecommerce.dto.request.AuthenticationRequest;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.enums.AccountStatus;
import com.myproject.ecommerce.enums.Gender;
import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.repository.UserRepository;
import com.myproject.ecommerce.security.handler.OAuth2LoginSuccessHandler;
import jakarta.transaction.Transactional;
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

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

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

    private static final String ADMIN_USERNAME = "adminUser";
    private static final String USER_USERNAME = "normalUser";
    private static final String TEST_PASSWORD = "testPass";

    private String adminToken;
    private String userToken;

    @BeforeEach
    void setup() throws Exception {
        // create ADMIN user
        User adminUser = User.builder()
                .gender(Gender.HIDE).build();

        // create Normal user
        User normalUser = User.builder()
                .gender(Gender.HIDE).build();

        // Setup ADMIN account
        Account adminAccount = Account.builder()
                .username(ADMIN_USERNAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .accountRoles(Set.of(Role.ADMIN))
                .accountStatus(AccountStatus.ACTIVE)
                .user(adminUser)
                .build();
        adminUser.setAccount(adminAccount);
        accountRepository.save(adminAccount);

        // Setup USER account
        Account userAccount = Account.builder()
                .username(USER_USERNAME)
                .password(passwordEncoder.encode(TEST_PASSWORD))
                .accountRoles(Set.of(Role.USER))
                .accountStatus(AccountStatus.ACTIVE)
                .user(normalUser)
                .build();
        normalUser.setAccount(userAccount);
        accountRepository.save(userAccount);

        // get token
        adminToken = getToken(ADMIN_USERNAME);
        userToken = getToken(USER_USERNAME);
    }

    // hepler login and get token
    private String getToken(String username) throws Exception {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .username(username)
                .password(TEST_PASSWORD)
                .build();

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response)
                .path("data")
                .path("token")
                .asText();
    }

    @Test
    void getAllUsers_withAminRole_shouldReturnList() throws Exception {
        mockMvc.perform(get("/api/users/list")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2)); // 2 account was prepared
    }

    @Test
    void getAllUsers_withUserRole_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/users/list")
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllUsers_withoutToken_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/users/list"))
                .andExpect(status().isUnauthorized());
    }
}
