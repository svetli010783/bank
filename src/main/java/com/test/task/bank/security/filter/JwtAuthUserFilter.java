package com.test.task.bank.security.filter;

import com.test.task.bank.domain.User;
import com.test.task.bank.repository.UserRepository;
import com.test.task.bank.security.AuthorizedUser;
import com.test.task.bank.security.utils.JwtHelper;
import com.test.task.bank.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JwtAuthUserFilter extends OncePerRequestFilter {
    UserRepository userRepository;
    AuthService authService;

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Optional<String> bearerTokenOptional = JwtHelper.extractJwtFromRequest(request);
        if (bearerTokenOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            log.warn("Token is empty user token in request {}", request);
            return;
        }
        String bearerToken = bearerTokenOptional.get();
        Map<String, Object> claims = authService.extractAllClaims(bearerToken);
        if (claims.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        String login = (String) claims.get("subject");
        Optional<User> userOptional = userRepository.findByLogin(login);
        if (userOptional.isEmpty()) {
            log.warn("User with login {} is empty in request {}", login, request);
            filterChain.doFilter(request, response);
            return;
        }
        User user = userOptional.get();

//        if (!jwtApiClient.isTokenValid(bearerToken, user.getPhoneNumber())) {
//            log.warn("Token {} is not valid user token in request {}", bearerToken, request);
//            filterChain.doFilter(request, response);
//            return;
//        }
        AuthorizedUser authorizedUser = new AuthorizedUser(
                user.getLogin(), user.getPassword(), user.getBankAccount(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")), user.getId());
        SecurityContextHolder.getContext().setAuthentication(authorizedUser);
        filterChain.doFilter(request, response);
    }
}
