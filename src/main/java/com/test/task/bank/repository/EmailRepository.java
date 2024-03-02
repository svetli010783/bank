package com.test.task.bank.repository;

import com.test.task.bank.domain.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    @Query(value = "SELECT * FROM email WHERE email = :email",
            nativeQuery = true)
    Optional<Email> findByEmail(String email);

}
