package com.example.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    Long id;

    @NotEmpty(message = "Name can not be empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Email address should be valid email")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{11})", message = "mobile number must be 11 digit")
    private String mobileNumber;

    private AccountsDto accountsDto;

}
