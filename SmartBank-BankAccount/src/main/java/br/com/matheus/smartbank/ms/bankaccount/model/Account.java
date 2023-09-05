package br.com.matheus.smartbank.ms.bankaccount.model;

import br.com.matheus.smartbank.ms.bankaccount.enums.AccountStatus;
import br.com.matheus.smartbank.ms.bankaccount.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Account extends OtherAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(nullable = false)
    private String accountHolderName;
    @Column(unique = true, nullable = false)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus;
    @Column(unique = true,nullable = false)
    private String cpf;
    @Column
    private double balance;

    public Account(String university, String expiration, String conjugateName, String conjugateCpf) {
        super(university, expiration, conjugateName, conjugateCpf);
    }

    @Override
    public String getUniversity() {
        return super.getUniversity();
    }

    @Override
    public String getExpiration() {
        return super.getExpiration();
    }

    @Override
    public String getConjugateName() {
        return super.getConjugateName();
    }

    @Override
    public String getConjugateCpf() {
        return super.getConjugateCpf();
    }

    @Override
    public void setUniversity(String university) {
        super.setUniversity(university);
    }

    @Override
    public void setExpiration(String expiration) {
        super.setExpiration(expiration);
    }

    @Override
    public void setConjugateName(String conjugateName) {
        super.setConjugateName(conjugateName);
    }

    @Override
    public void setConjugateCpf(String conjugateCpf) {
        super.setConjugateCpf(conjugateCpf);
    }
}
