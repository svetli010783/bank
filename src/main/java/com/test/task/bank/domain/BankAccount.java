package com.test.task.bank.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigInteger;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE bank_account SET deleted_at = NOW() WHERE id=?")
public class BankAccount extends PersistentObject {
    @Column(name = "current_balance")
    BigInteger currentBalance;
    @Column(name = "start_balance")
    BigInteger startBalance;
    @Column(name = "currency")
    String currency;
    @OneToMany(targetEntity = Transaction.class)
    @Column(name = "transactions")
    final Set<Transaction> transactions  = new HashSet<>();
    @OneToOne(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    User user;

    public void setCurrency(Currency currency) {
        this.currency = currency.getCurrencyCode();
    }
    public Currency getCurrency() {
        return Currency.getInstance(currency);
    }
}
