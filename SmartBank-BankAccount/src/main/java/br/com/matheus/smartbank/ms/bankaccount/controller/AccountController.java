package br.com.matheus.smartbank.ms.bankaccount.controller;

import br.com.matheus.smartbank.ms.bankaccount.dto.AccountDTO;
import br.com.matheus.smartbank.ms.bankaccount.enums.AccountStatus;
import br.com.matheus.smartbank.ms.bankaccount.model.Account;
import br.com.matheus.smartbank.ms.bankaccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String testeApi(){
        return "sucess api test";
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO createAccount(@RequestBody AccountDTO dto){
       return accountService.createAccount(dto);
    }

    @PutMapping("/{id}")
    public AccountDTO updateAccount(@PathVariable UUID id, @RequestBody AccountDTO dto){
        return accountService.updateAccount(id, dto);
    }

    @GetMapping("/{accountNumber}")
    public AccountDTO findAccountByAccountNumber(@PathVariable String accountNumber){
        return accountService.findAccountByAccountNumber(accountNumber);
    }

    @GetMapping("/{accountNumber}/withdraw/{amount}")
    public AccountDTO withdrawBalance(@PathVariable String accountNumber, @PathVariable double amount){
        return accountService.withdraw(accountNumber, amount);
    }

    @GetMapping("/{accountNumber}/deposit/{amount}")
    public AccountDTO depositBalance(@PathVariable String accountNumber, @PathVariable double amount){
        return accountService.deposit(accountNumber, amount);
    }

    @GetMapping("/{sourceAccount}/transfer/{destinationAccount}/{amount}")
    public AccountDTO transferBalance(@PathVariable String sourceAccount, @PathVariable String destinationAccount, @PathVariable double amount){
        return accountService.transfer(sourceAccount, destinationAccount, amount);
    }

    @GetMapping("/{accountNumber}/balance")
    public double consultBalance(@PathVariable String accountNumber){
        return accountService.consultBalance(accountNumber);
    }

    /*
    create new account ¬
    update account ¬
    find my account ¬
    withdraw balance ¬
    deposit amount ¬
    transfer balance ¬
    ------------------
    list all accounts ¬
    delete account ¬
    ------------------
    add status account ¬
    ------------------
    add logica juros das transações da conta ¬

    conta de estudantes deve seguir do
    nome da universidade e validade(ano de conclusao) ¬
    conta conjunta deve seguir com nome e cpf do conjungue,
    os dois podem fazer movimentações ¬
    ------------------
    metodo - consultar saldo ¬

    fazer verificacao, ¬
    se o status da conta for esperando aprovacao,
    n poderá fazer nenhuma transação,
    se for bloqueado nao deve ser permitido

    metodo aprovar-ativar conta ¬


    metodo para bloquear e ativar conta (cobrar juros if tal dias..) ¬
     */

    @GetMapping()
    public List<Account> listAllAccounts(){
        return accountService.listAll();
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable UUID id){
        accountService.delete(id);
    }

    @GetMapping("/{id}/block")
    public AccountStatus blocksAccount(@PathVariable UUID id){
        return accountService.blockAccount(id);
    }

    @GetMapping("/{id}/active")
    public AccountStatus activeAccount(@PathVariable UUID id){
        return accountService.activeAccount(id);
    }
}
