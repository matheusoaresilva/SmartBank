package br.com.matheus.smartbank.ms.bankaccount.repository;

import br.com.matheus.smartbank.ms.bankaccount.dto.AccountDTO;
import br.com.matheus.smartbank.ms.bankaccount.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByAccountNumber(String accountNumber);

}
