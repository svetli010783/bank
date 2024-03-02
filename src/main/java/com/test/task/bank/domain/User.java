package com.test.task.bank.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id=?")
public class User extends PersistentObject {
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Email.class)
    @Column(name = "emails")
    final List<Email> emails = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, targetEntity = PhoneNumber.class)
    @Column(name = "phone_numbers")
    final List<PhoneNumber> phoneNumbers = new ArrayList<>();
    @Column(name = "birthdate")
    LocalDate birthdate;
    @Column(name = "name")
    String name;
    @Column(name = "surname")
    String surname;
    @Column(name = "patronymic")
    String patronymic;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    BankAccount bankAccount;
    @Column(name = "login")
    String login;
    @Column(name = "password")
    String password;
}
