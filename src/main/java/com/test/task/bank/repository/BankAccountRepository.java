package com.test.task.bank.repository;

import com.test.task.bank.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query(value = """
            UPDATE bank_account
            SET current_balance = current_balance * 1.05
            WHERE (current_balance * 1.05) <= (start_balance * 2.07)
            """, nativeQuery = true)
    @Modifying
    @Transactional
    int chargeInterest();
}

