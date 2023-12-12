package com.example.accounts.service.impl;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Accounts;
import com.example.accounts.entity.Customer;
import com.example.accounts.exception.CustomerAlreadyExistException;
import com.example.accounts.exception.ResourceNotfoundException;
import com.example.accounts.mappper.AccountMapper;
import com.example.accounts.mappper.CustomerMapper;
import com.example.accounts.repository.AccountsRepository;
import com.example.accounts.repository.CustomerRepository;
import com.example.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> customrMobileNumber = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (customrMobileNumber.isPresent()) {
            throw new CustomerAlreadyExistException("""
                    Customer already present with this ($customerMobileNumber).
                    """.replace("$customerMobileNumber", customerDto.getMobileNumber()));
        }
        customer.setCreateAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer customerSaved = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(customerSaved));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getId());
        long newAccountNumber = 10000000L + new Random().nextInt(90000000);
        newAccount.setAccountNumber(newAccountNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotfoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getId()).orElseThrow(
                () -> new ResourceNotfoundException("Accounts", "customerId", customer.getId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }
}


