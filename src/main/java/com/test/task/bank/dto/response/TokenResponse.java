package com.test.task.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class TokenResponse {
    private String token;
    private ZonedDateTime expirationAt;
}
