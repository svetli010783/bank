package com.test.task.bank.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@NoArgsConstructor
@Data
@ToString
public class UserFilterDto {
    LocalDate date;
    String phoneNumber;
    String name;
    String surname;
    String patronymic;
    @Email
    String email;
}
