package com.test.task.bank.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bank account")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Validated
public class BankAccountController {
}
