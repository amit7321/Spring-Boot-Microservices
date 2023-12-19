package com.example.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Account number must not be empty")
    @Pattern(regexp = "(^$|[0-9]{11})", message = "account number must  be 10 digit")
    private Long accountNumber;

    @NotEmpty(message = "Account type must not be empty")
    private String accountType;

    @NotEmpty(message = "Branch Address must not be empty")
    private String branchAddress;
}
