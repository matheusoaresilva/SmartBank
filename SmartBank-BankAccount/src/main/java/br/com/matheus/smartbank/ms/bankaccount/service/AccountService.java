package br.com.matheus.smartbank.ms.bankaccount.service;

import br.com.matheus.smartbank.ms.bankaccount.dto.AccountDTO;
import br.com.matheus.smartbank.ms.bankaccount.enums.AccountStatus;
import br.com.matheus.smartbank.ms.bankaccount.enums.AccountType;
import br.com.matheus.smartbank.ms.bankaccount.service.exception.AccountNotAllowedException;
import br.com.matheus.smartbank.ms.bankaccount.service.exception.InsufficientBalanceException;
import br.com.matheus.smartbank.ms.bankaccount.model.Account;
import br.com.matheus.smartbank.ms.bankaccount.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;



    public AccountDTO createAccount(AccountDTO dto){
        dto.setAccountStatus(AccountStatus.PENDING_APPROVAL);
        Account account = accountRepository.save(modelMapper.map(dto, Account.class));
        return modelMapper.map(account, AccountDTO.class);
    }

    public AccountDTO updateAccount(UUID id, AccountDTO dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id: "+ id + " not found!"));

        modelMapper.map(dto, account);

        Account savedAccount = accountRepository.save(account);

        return modelMapper.map(savedAccount, AccountDTO.class);
    }

    public AccountDTO findAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account with this number "+ accountNumber+" not found!"));

        return modelMapper.map(account, AccountDTO.class);
    }

    public AccountDTO withdraw(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(()-> new EntityNotFoundException("Account with this number "+ accountNumber + " not found!"));

        if (account.getAccountStatus() == AccountStatus.BLOCKED
                || account.getAccountStatus() == AccountStatus.CLOSED
                || account.getAccountStatus() == AccountStatus.INACTIVE
                || account.getAccountStatus() == AccountStatus.PENDING_APPROVAL){
            throw new AccountNotAllowedException("The current status of the account is "
                            + account.getAccountStatus());
        }else{
            if (amount > account.getBalance()){
                throw new InsufficientBalanceException("Insufficient balance");
            }

            if (account.getAccountType() == AccountType.SAVING_ACCOUNT){
                double tax = 3.0;
                System.out.println("TAXA: " + tax);
                double balance = (account.getBalance() - amount) - tax;
                account.setBalance(balance);
            } else if (account.getAccountType() == AccountType.CHECKING_ACCOUNT || account.getAccountType() == AccountType.JOINT_ACCOUNT) {
                double tax = 2.0;
                System.out.println("TAXA: " + tax);
                double balance = (account.getBalance() - amount) - tax;
                account.setBalance(balance);
            } else if (account.getAccountType() == AccountType.BUSINESS_ACCOUNT) {
                double tax = 5.0;
                System.out.println("TAXA: " + tax);
                double balance = (account.getBalance() - amount) - tax;
                account.setBalance(balance);
            }
        }

        Account savedAccount = accountRepository.save(account);
        System.out.println("SACADO COM SUCESSO " + amount + "\nnovo saldo: "+ account.getBalance());

//        savedAccount = (Account) withdraw;
        return modelMapper.map(savedAccount, AccountDTO.class);
    }

    public AccountDTO deposit(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account with this number "+ accountNumber + " not found!"));

        if (account.getAccountType() == AccountType.SAVING_ACCOUNT){
            double tax = 0.3;
            System.out.println("TAXA: " + tax);
            double balance = (account.getBalance() + amount) - tax;
            account.setBalance(balance);
        } else if (account.getAccountType() == AccountType.CHECKING_ACCOUNT || account.getAccountType() == AccountType.JOINT_ACCOUNT) {
            double tax = 0.8;
            System.out.println("TAXA: " + tax);
            double balance = (account.getBalance() + amount) - tax;
            account.setBalance(balance);
        } else if (account.getAccountType() == AccountType.BUSINESS_ACCOUNT) {
            double tax = 1.0;
            System.out.println("TAXA: " + tax);
            double balance = (account.getBalance() + amount) - tax;
            account.setBalance(balance);
        }

        Account savedAccount = accountRepository.save(account);
        System.out.println("DEPOSITADO COM SUCESSO " + amount + "\n novo saldo "+ account.getBalance());

        return modelMapper.map(savedAccount, AccountDTO.class);
    }

    public AccountDTO transfer(String sourceAccount, String destinationAccount, double amount) {
        Account acc1 = accountRepository.findByAccountNumber(sourceAccount)
                .orElseThrow(()-> new EntityNotFoundException("Account with this number "+ sourceAccount + " not found!"));

        Account acc2 = accountRepository.findByAccountNumber(destinationAccount)
                .orElseThrow(()-> new EntityNotFoundException("Account with this number "+ destinationAccount + " not found!"));

        if (amount > acc1.getBalance()){
            throw new InsufficientBalanceException("Insufficient balance");
        }
        System.out.println("ANTES DA TRANSFERENCIA " + amount
                + "\nsaldo do remetente "
                + acc1.getBalance()
                + "\nsaldo do destinatario "
                + acc2.getBalance());

        double balanceAcc1 = acc1.getBalance() - amount;
        double balanceAcc2 = acc2.getBalance() + amount;

        acc1.setBalance(balanceAcc1);
        acc2.setBalance(balanceAcc2);

        Account savedAcc1 = accountRepository.save(acc1);
        Account savedAcc2 = accountRepository.save(acc2);
        System.out.println("TRANSFERIDO COM SUCESSO " + amount
                + "\nnovo saldo do remetente "
                + acc1.getBalance()
                + "\nnovo saldo do destinatario "
                + acc2.getBalance());

        return modelMapper.map(acc1, AccountDTO.class);
    }

    public double consultBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account with this number "+ accountNumber + " not found!"));

        return account.getBalance();
    }
    public List<Account> listAll() {
        return accountRepository.findAll();
    }

    public void delete(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Account with this id "+ id +" not found!"));
        System.out.println("CONTA "+ id + " DELETADO COM SUCESSO");
        accountRepository.deleteById(id);
    }


    public AccountStatus blockAccount(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id: "+ id + " not found!"));

        account.setAccountStatus(AccountStatus.BLOCKED);
        accountRepository.save(account);
        return account.getAccountStatus();
    }

    public AccountStatus activeAccount(UUID id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id: "+ id + " not found!"));

        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        return account.getAccountStatus();
    }
}
