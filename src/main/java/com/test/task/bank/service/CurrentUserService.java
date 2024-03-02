package com.test.task.bank.service;

import com.test.task.bank.security.UserClaims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    public CurrentUserService() {
    }

    public UserClaims getUser() {
        return (UserClaims) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }
}
