package com.test.task.bank.service;

import com.test.task.bank.domain.*;
import com.test.task.bank.domain.enums.TransactionState;
import com.test.task.bank.domain.enums.TransactionType;
import com.test.task.bank.dto.request.UserFilterDto;
import com.test.task.bank.dto.response.TokenResponse;
import com.test.task.bank.dto.response.UserResponse;
import com.test.task.bank.repository.EmailRepository;
import com.test.task.bank.repository.PhoneNumberRepository;
import com.test.task.bank.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    CurrentUserService currentUserService;
    AuthService authService;
    EmailRepository emailRepository;
    PhoneNumberRepository phoneNumberRepository;

    public UserService(UserRepository userRepository,
                       CurrentUserService currentUserService,
                       AuthService authService,
                       EmailRepository emailRepository,
                       PhoneNumberRepository phoneNumberRepository) {
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
        this.authService = authService;
        this.emailRepository = emailRepository;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    public TokenResponse registration(
            String phoneNumber,
            String email,
            BigInteger startBalance,
            Currency currency,
            String login,
            String password,
            LocalDate birthdate,
            String name,
            String surname,
            String patronymic) {
        if (emailRepository.findByEmail(email).isPresent())
            throw new NotUniqueUserParamException("email", email);
        if (phoneNumberRepository.findByPhoneNumber(phoneNumber).isPresent())
            throw new NotUniqueUserParamException("phoneNumber", phoneNumber);
        if (userRepository.findByLogin(login).isPresent())
            throw new NotUniqueUserParamException("login", login);

        BankAccount bankAccount = new BankAccount();

        User sender = userRepository.findByLogin("superuser").get();

        Transaction transaction = new Transaction(
                sender.getBankAccount(),
                bankAccount,
                startBalance,
                TransactionState.CREATE,
                TransactionType.REGISTRATION);

        bankAccount.getTransactions().add(transaction);

        transaction.setState(TransactionState.PROCESSING);

        bankAccount.setStartBalance(startBalance);
        bankAccount.setCurrentBalance(startBalance);
        bankAccount.setCurrency(currency);

        User user = User.builder()
                .login(login)
                .password(password)
                .bankAccount(bankAccount)
                .name(name)
                .surname(surname)
                .patronymic(patronymic)
                .birthdate(birthdate)
                .build();

        user.getEmails().add(Email.builder().email(email).build());
        user.getPhoneNumbers().add(PhoneNumber.builder().phoneNumber(phoneNumber).build());

        userRepository.save(user);

        transaction.setState(TransactionState.COMPLETE);
        log.info(transaction.toString());
        TokenResponse token = authService.buildToken(FillingClaims.fillingClaimsForUser(user));
        return token;
    }

    public Page<UserResponse> getByFilter(Pageable pageable, UserFilterDto userFilterDto) {
        String fio = userFilterDto.getSurname() + userFilterDto.getName() + userFilterDto.getPatronymic() + "%";
        List<User> result = userRepository.findByFilter(userFilterDto.getDate(), userFilterDto.getPhoneNumber(), userFilterDto.getEmail(), fio);
        Page<UserResponse> pageResult = new PageImpl<>(result.stream().map(UserResponse::new).toList(), pageable, result.size());
        return pageResult;
    }

    public void addPhoneNumber(String phoneNumber) {
        if (phoneNumberRepository.findByPhoneNumber(phoneNumber).isPresent())
            throw new NotUniqueUserParamException("phoneNumber", phoneNumber);
        User user = userRepository.findByLogin(currentUserService.getUser().getUsername()).get();
        user.getPhoneNumbers().add(PhoneNumber.builder().phoneNumber(phoneNumber).build());
        userRepository.save(user);
    }

    public void addEmail(String email) {
        if (emailRepository.findByEmail(email).isPresent())
            throw new NotUniqueUserParamException("email", email);
        User user = userRepository.findByLogin(currentUserService.getUser().getUsername()).get();
        user.getEmails().add(Email.builder().email(email).build());
        userRepository.save(user);
    }

    public void removePhoneNumber(String phoneNumber) {
        User user = userRepository.findByLogin(currentUserService.getUser().getUsername()).get();
        if (user.getPhoneNumbers().size() == 1)
            throw new NotLastUserParamException("phoneNumber", phoneNumber);
        user.getPhoneNumbers().remove(PhoneNumber.builder().phoneNumber(phoneNumber).build());
        userRepository.save(user);
    }

    public void removeEmail(String email) {
        User user = userRepository.findByLogin(currentUserService.getUser().getUsername()).get();
        if (user.getEmails().size() == 1)
            throw new NotLastUserParamException("email", email);
        user.getEmails().remove(Email.builder().email(email).build());
        userRepository.save(user);
    }

    static class NotUniqueUserParamException extends ResponseStatusException {
        public NotUniqueUserParamException(String param, String paramValue) {
            super(HttpStatus.BAD_REQUEST, String.format("%s with value %s already exist", param, paramValue));
        }
    }

    static class NotLastUserParamException extends ResponseStatusException {
        public NotLastUserParamException(String param, String paramValue) {
            super(HttpStatus.BAD_REQUEST, String.format("%s with value %s is last your %s. You can not remove it", param, paramValue, param));
        }
    }
}
