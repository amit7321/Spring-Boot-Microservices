package com.example.accounts.service.impl;

import com.example.accounts.constants.AccountsConstants;
import com.example.accounts.dto.AccountsDto;
import com.example.accounts.dto.CustomerDto;
import com.example.accounts.entity.Account;
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
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer customerSaved = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(customerSaved));
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getId());
        long newAccountNumber = 10000000L + new Random().nextInt(90000000);
        newAccount.setAccountNumber(newAccountNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");

        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotfoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account accounts = accountsRepository.findByCustomerId(customer.getId()).orElseThrow(
                () -> new ResourceNotfoundException("Accounts", "customerId", customer.getId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Account account = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotfoundException("Accounts", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccounts(accountsDto, account);
            account = accountsRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotfoundException("Customer", "CustomerId", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
               customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;

    }
}


