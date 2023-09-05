package br.com.matheus.smartbank.ms.bankaccount.dto;

import br.com.matheus.smartbank.ms.bankaccount.enums.AccountStatus;
import br.com.matheus.smartbank.ms.bankaccount.enums.AccountType;
import br.com.matheus.smartbank.ms.bankaccount.model.OtherAccount;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper=false)
public class AccountDTO extends OtherAccount {
    private UUID id;
    private String accountHolderName;
    private String accountNumber;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private String cpf;
    private double balance;
}
