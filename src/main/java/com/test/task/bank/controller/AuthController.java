package com.test.task.bank.controller;

import com.test.task.bank.dto.request.UserRequest;
import com.test.task.bank.dto.response.TokenResponse;
import com.test.task.bank.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Validated
public class AuthController {
    UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("registration")
    @Operation(operationId = "registration")
    public TokenResponse registration(@Valid @RequestBody UserRequest userRequest) {
        return userService.registration(
                userRequest.getPhoneNumber(),
                userRequest.getEmail(),
                userRequest.getStartBalance(),
                userRequest.getCurrency(),
                userRequest.getLogin(),
                userRequest.getPassword(),
                userRequest.getBirthdate(),
                userRequest.getName(),
                userRequest.getSurname(),
                userRequest.getPatronymic());
    }
}
