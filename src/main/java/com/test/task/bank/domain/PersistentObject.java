package com.test.task.bank.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@MappedSuperclass
@EqualsAndHashCode
@Getter
@Setter
public abstract class PersistentObject {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_at", insertable = false, columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private ZonedDateTime createdAt;
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;
}