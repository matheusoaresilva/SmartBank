package br.com.matheus.smartbank.ms.bankaccount.service.exception;

public class InsufficientBalanceException extends RuntimeException {

    public InsufficientBalanceException (String e){
        super(e);
    }
}
