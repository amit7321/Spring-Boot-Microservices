package com.example.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information "
)
public class AccountsDto {

    @NotEmpty(message = "Account number must not be empty")
    @Pattern(regexp = "(^$|[0-9]{11})", message = "account number must  be 10 digit")
    private Long accountNumber;

    @Schema(
            description = "Account type of eazy bank account",
            example = "savings"
    )
    @NotEmpty(message = "Account type must not be empty")
    private String accountType;

    @NotEmpty(message = "Branch Address must not be empty")
    private String branchAddress;
}
