package com.test.task.bank.service;

import com.test.task.bank.domain.BankAccount;
import com.test.task.bank.domain.Transaction;
import com.test.task.bank.domain.enums.TransactionState;
import com.test.task.bank.domain.enums.TransactionType;
import com.test.task.bank.repository.BankAccountRepository;
import com.test.task.bank.utils.BankAccountFactory;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
@EnableTransactionManagement
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankAccountService {
    BankAccountRepository bankAccountRepository;
    CurrentUserService currentUserService;

    public BankAccountService(BankAccountRepository bankAccountRepository, CurrentUserService currentUserService) {
        this.bankAccountRepository = bankAccountRepository;
        this.currentUserService = currentUserService;

    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ
    )
    public void transfer(Long recipientId, BigInteger transferAmount) {
        BankAccount sender = currentUserService.getUser().getBankAccount();

        if (!BankAccountFactory.beforeTransferCheck(sender, transferAmount))
            throw new InsufficientFunds();

        Optional<BankAccount> recipientOptional = bankAccountRepository.findById(recipientId);
        if (recipientOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bank account recipient with id: " + recipientId + " was not found");

        BankAccount recipient = recipientOptional.get();

        Transaction transaction = new Transaction(sender, recipient, transferAmount, TransactionState.CREATE, TransactionType.TRANSFER);
        transaction.setState(TransactionState.PROCESSING);

        sender.setCurrentBalance(new BigInteger(String.valueOf(sender.getCurrentBalance().signum() - transferAmount.signum())));
        recipient.setCurrentBalance(new BigInteger(String.valueOf(sender.getCurrentBalance().signum() + transferAmount.signum())));

        transaction.setState(TransactionState.COMPLETE);

        bankAccountRepository.saveAll(List.of(sender, recipient));

        log.info(transaction.toString());
    }

    @Scheduled(fixedRate = 60000)
    public void chargeInterest() {
        bankAccountRepository.chargeInterest();
    }

    static class InsufficientFunds extends ResponseStatusException {
        public InsufficientFunds() {
            super(HttpStatus.BAD_REQUEST, "There are not enough funds in your account");
        }
    }
}
