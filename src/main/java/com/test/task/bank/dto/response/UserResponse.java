package com.test.task.bank.dto.response;

import com.test.task.bank.domain.Email;
import com.test.task.bank.domain.PhoneNumber;
import com.test.task.bank.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    Set<String> emails;
    Set<String> phoneNumbers;
    LocalDate birthdate;
    String name;
    String surname;
    String patronymic;
    Long bankAccountId;

    public UserResponse(User user) {
        this.id = user.getId();
        this.emails = user.getEmails().stream().map(Email::getEmail).collect(Collectors.toSet());
        this.phoneNumbers = user.getPhoneNumbers().stream().map(PhoneNumber::getPhoneNumber).collect(Collectors.toSet());
        this.birthdate = user.getBirthdate();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.patronymic = user.getPatronymic();
        this.bankAccountId = user.getBankAccount().getId();
    }

}
