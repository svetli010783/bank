package com.test.task.bank.dto.request;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Currency;

@NoArgsConstructor
@ToString
public class UserRequest {
    @NotNull
    @Pattern(regexp = "^(\\+\\d{11}$)")
    String phoneNumber;
    @NotNull
    @Email
    String email;
    @NotNull
    @Positive(message = "startBalance must be positive")
    BigInteger startBalance;
    String currency;
    @NotNull
    @Size(max = 255)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    String login;
    @Size(max = 255)
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\\w\\s]).{8,}")
    String password;
    LocalDate birthdate;
    String name;
    String surname;
    String patronymic;

    public Currency getCurrency() {
        return Currency.getInstance(currency);
    }

    public void setCurrency(Currency currency) {
        this.currency = currency.getCurrencyCode();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStartBalance(BigInteger startBalance) {
        this.startBalance = startBalance;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public BigInteger getStartBalance() {
        return startBalance;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
