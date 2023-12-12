package com.example.accounts.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private AccountsDto accountsDto;

}
