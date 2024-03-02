package com.test.task.bank.repository;

import com.test.task.bank.domain.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
    @Query(value = "SELECT * FROM phone_number WHERE phone_number = :phoneNumber",
            nativeQuery = true)
    Optional<PhoneNumber> findByPhoneNumber(String phoneNumber);
}
