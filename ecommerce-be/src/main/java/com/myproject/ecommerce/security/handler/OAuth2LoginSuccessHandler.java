package com.myproject.ecommerce.security.handler;

import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.enums.AccountStatus;
import com.myproject.ecommerce.enums.AuthProvider;
import com.myproject.ecommerce.enums.Gender;
import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.service.JwtService;
import com.myproject.ecommerce.utils.NickNameRandomUtils;
import com.myproject.ecommerce.utils.UserCodeRandomUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final AccountRepository accountRepository;
    private final JwtService jwtService;

    @Value("${app.oauth2.redirect.frontend-url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");
        String name = user.getAttribute("name");
        String avatarUrl = extractAvatar(user);

        Account account = accountRepository.findByEmail(email)
                .orElseGet(() -> {
                    // set roles
                    Set<Role> accountRoles = new HashSet<>();
                    accountRoles.add(Role.USER);

                    // set auth provider
                    String registrationId =
                            ((OAuth2AuthenticationToken) authentication)
                                    .getAuthorizedClientRegistrationId();
                    AuthProvider provider = AuthProvider.valueOf(registrationId.toUpperCase());

                    Account newAcc = Account.builder()
                            .email(email)
                            .authProvider(provider)
                            .accountStatus(AccountStatus.ACTIVE)
                            .accountRoles(accountRoles)
                            .build();

                    User newUser = User.builder()
                            .nickName(NickNameRandomUtils.generateDefaultNickName())
                            .userCode(UserCodeRandomUtils.generateUserCode())
                            .gender(Gender.HIDE)
                            .avatarUrl(avatarUrl)
                            .fullName(name)
                            .build();

                    newAcc.setUser(newUser);
                    newUser.setAccount(newAcc);

                    return accountRepository.save(newAcc);
                });

        String token = jwtService.generateToken(account);

        String redirectUrl = frontendUrl
                + "/ecommerce-fe/pages/social-login-success.html?token="
                + URLEncoder.encode(token, StandardCharsets.UTF_8);

        // redirect
        response.sendRedirect(redirectUrl);
    }


    private String extractAvatar(OAuth2User user) {
        Object pictureObj = user.getAttribute("picture");

        if (pictureObj instanceof String) {
            // Google
            return (String) pictureObj;
        }

        if (pictureObj instanceof Map<?, ?> pictureMap) {
            // Facebook
            Object dataObj = pictureMap.get("data");

            if (dataObj instanceof Map<?, ?> dataMap) {
                Object urlObj = dataMap.get("url");

                if (urlObj instanceof String url) {
                    return url;
                }
            }
        }

        return null;
    }
}
