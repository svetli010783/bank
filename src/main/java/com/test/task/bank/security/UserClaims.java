package com.test.task.bank.security;

import com.test.task.bank.domain.BankAccount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserClaims {
    private Long id;
    private String username;
    private BankAccount bankAccount;

    public UserClaims(Long id, String username, BankAccount bankAccount) {
        this.id = id;
        this.username = username;
        this.bankAccount = bankAccount;
    }
}
