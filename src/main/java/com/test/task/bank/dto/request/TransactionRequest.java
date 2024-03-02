package com.test.task.bank.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class TransactionRequest {
    @NotNull
    @Positive
    Long recipientId;
    @Positive
    @NotNull
    Double transferAmount;
}
