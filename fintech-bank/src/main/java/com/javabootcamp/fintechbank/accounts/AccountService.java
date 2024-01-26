package com.javabootcamp.fintechbank.accounts;

import com.javabootcamp.fintechbank.exceptions.InternalServerException;
import com.javabootcamp.fintechbank.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountResponse> getAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(acc -> new AccountResponse(acc.getNo(), acc.getType(), acc.getName(), acc.getBalance()))
                .toList();
    }

    public AccountResponse createAccount(AccountRequest accountRequest){

        Account account = new Account();
        account.setType(accountRequest.type());
        account.setName(accountRequest.name());
        account.setBalance(accountRequest.balance());

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new InternalServerException("Failed to create account");
        }
        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }

    public AccountResponse depositAccount(Integer accountNo, DepositRequest depositRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        Account account = optionalAccount.get();
        Double newBalance = account.getBalance() + depositRequest.amount();
        account.setBalance(newBalance);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new InternalServerException("Failed to deposit");
        }
        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }
    public AccountResponse withDrawAccount(Integer accountNo, WithdrawRequest withdrawRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }

        Account account = optionalAccount.get();
        Double newBalance = account.getBalance() - withdrawRequest.amount();
        account.setBalance(newBalance);

        try {
            accountRepository.save(account);
        } catch (Exception e) {
            throw new InternalServerException("Failed to withdraw");
        }
        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }
    public AccountResponse transferAccount(Integer accountNo,Integer targetAccountNo, TransferRequest transferRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        Optional<Account> optionalTargetAccountNo = accountRepository.findById(targetAccountNo);
        if (optionalTargetAccountNo.isEmpty()) {
            throw new NotFoundException("Account target not found");
        }

        Account account = optionalAccount.get();
        Double newBalance = account.getBalance() - transferRequest.amount();
        account.setBalance(newBalance);

        Account accountTarget = optionalTargetAccountNo.get();
        Double newBalanceTarget = accountTarget.getBalance() + transferRequest.amount();
        accountTarget.setBalance(newBalanceTarget);
        try {
            accountRepository.save(account);
//            accountRepository.save(accountTarget);
        } catch (Exception e) {
            throw new InternalServerException("Failed to withdraw");
        }
        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }
    public AccountResponse findAccountByAccountNo(Integer accountNo) {
        Optional<Account> optionalAccount = accountRepository.findById(accountNo);
        if (optionalAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        Account account = optionalAccount.get();
        return new AccountResponse(account.getNo(), account.getType(), account.getName(), account.getBalance());
    }
}
