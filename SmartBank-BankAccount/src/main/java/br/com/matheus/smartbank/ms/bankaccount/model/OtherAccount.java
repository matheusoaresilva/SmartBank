package br.com.matheus.smartbank.ms.bankaccount.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class OtherAccount {

    private String university;
    private String expiration;
    private String conjugateName;
    private String conjugateCpf;
}
