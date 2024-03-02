package com.test.task.bank.controller;

import com.test.task.bank.dto.request.UserFilterDto;
import com.test.task.bank.dto.response.UserResponse;
import com.test.task.bank.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Validated
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("add-phone-number")
    @Secured("ROLE_USER")
    public void addPhoneNumber(@Valid @RequestParam @NotNull @Pattern(regexp = "^(\\+\\d{11}$)") String phoneNumber) {
        userService.addPhoneNumber(phoneNumber);
    }

    @PostMapping("add-email")
    @Secured("ROLE_USER")
    public void addEmail(@Valid @RequestParam @NotNull @Email String email) {
        userService.addEmail(email);
    }

    @DeleteMapping("remove-phone-number")
    @Secured("ROLE_USER")
    public void removePhoneNumber(@Valid @RequestParam @NotNull @Pattern(regexp = "^(\\+\\d{11}$)") String phoneNumber) {
        userService.removePhoneNumber(phoneNumber);
    }

    @DeleteMapping("remove-email")
    @Secured("ROLE_USER")
    public void removeEmail(@Valid @RequestParam @NotNull @Email String email) {
        userService.removeEmail(email);
    }

    @Secured("ROLE_USER")
    @GetMapping("by-filter")
    public Page<UserResponse> getByFilter(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "15") @Min(1) Integer limit,
            @Valid UserFilterDto userFilterDto) {
        return userService.getByFilter(PageRequest.of(offset, limit), userFilterDto);
    }


}
