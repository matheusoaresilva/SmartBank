package br.com.matheus.smartbank.ms.bankaccount.service.exception;

public class AccountNotAllowedException extends RuntimeException {
    public AccountNotAllowedException(String e) {
        super(e);
    }
}
