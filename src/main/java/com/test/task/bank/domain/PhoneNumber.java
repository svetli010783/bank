package com.test.task.bank.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Entity
@Table(name = "phone_number")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE phone_number SET deleted_at = NOW() WHERE id=?")
public class PhoneNumber extends PersistentObject {
    @Column(name = "phone_number", unique = true)
    String phoneNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), phoneNumber, owner);
    }
}
