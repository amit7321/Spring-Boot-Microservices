package com.example.accounts.service;

import com.example.accounts.dto.CustomerDto;

public interface IAccountService {

    void createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);
}
