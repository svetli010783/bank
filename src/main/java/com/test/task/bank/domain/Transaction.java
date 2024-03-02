package com.test.task.bank.domain;

import com.test.task.bank.domain.enums.TransactionState;
import com.test.task.bank.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigInteger;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE transaction SET deleted_at = NOW() WHERE id=?")
public class Transaction extends PersistentObject {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    BankAccount sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    BankAccount recipient;
    @Column(name = "transfer_amount")
    BigInteger transferAmount;
    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    TransactionState state;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    TransactionType type;
}
