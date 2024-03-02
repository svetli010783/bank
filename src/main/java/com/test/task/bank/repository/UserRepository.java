package com.test.task.bank.repository;

import com.test.task.bank.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users WHERE login = :login",
            nativeQuery = true)
    Optional<User> findByLogin(String login);

    @Query(value = "SELECT * FROM users " +
            "WHERE (coalesce(:date,null) IS NULL OR birthdate >= :date) " +
            "AND (:phoneNumber IS NULL OR :phoneNumber = any (phone_numbers)) " +
            "AND (:email IS NULL OR :email = any (emails)) " +
            "AND (:fio IS NULL OR CONCAT(surname, name, patronymic) like :fio) ",
            nativeQuery = true)

    List<User> findByFilter(LocalDate date, String phoneNumber, String email, String fio);
}