package com.test.task.bank.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Entity
@Table(name = "email")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE email SET deleted_at = NOW() WHERE id=?")
public class Email extends PersistentObject {
    @Column(name = "email", unique = true)
    String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    User owner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email) && Objects.equals(owner, email1.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, owner);
    }
}
