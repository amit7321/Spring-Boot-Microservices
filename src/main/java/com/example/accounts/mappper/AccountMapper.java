package com.example.accounts.mappper;

import com.example.accounts.dto.AccountsDto;
import com.example.accounts.entity.Account;

public class AccountMapper {

    public static AccountsDto mapToAccountsDto(Account accounts, AccountsDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static Account mapToAccounts(AccountsDto accountsDto, Account accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accounts.getBranchAddress());
        return accounts;
    }
}
