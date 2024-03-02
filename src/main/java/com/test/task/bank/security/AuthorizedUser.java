package com.test.task.bank.security;

import com.test.task.bank.domain.BankAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthorizedUser extends User implements Authentication {

    private final Collection<GrantedAuthority> authority;
    private final Long id;
    private final BankAccount bankAccount;

    public AuthorizedUser(String login, String password, BankAccount bankAccount,
                          Collection<? extends GrantedAuthority> authorities,
                          Long id) {
        super(
                login,
                password,
                authorities
        );
        this.bankAccount = bankAccount;
        this.id = id;
        this.authority = this.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return new UserClaims(id,super.getUsername(),bankAccount);
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.setAuthenticated(isAuthenticated);
    }

    @Override
    public String getName() {
        return super.getUsername();
    }
}
