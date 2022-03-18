package com.capgemini.internet.banking.services;
import com.capgemini.internet.banking.dto.TransactionDepositDto;
import com.capgemini.internet.banking.dto.TransactionWithDrawDto;
import com.capgemini.internet.banking.models.ClientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    Page<ClientModel> findAll(Pageable pageable);

    ClientModel save(ClientModel clientModel);

    Optional<ClientModel> findByid(Long clientId);

    ClientModel withdraw(Optional<ClientModel> resultClient, BigDecimal value);

    ResponseEntity<Object> validateRulesWithDraw(TransactionWithDrawDto transaction, Optional<ClientModel> resultClient);

    ResponseEntity<Object>  validateRulesDeposit(TransactionDepositDto transaction, Optional<ClientModel> resultClient);

    ClientModel deposit(Optional<ClientModel> resultClient, BigDecimal deposit);

    ResponseEntity<Object> validAndGetResponseEntityOneClient(Optional<ClientModel> clientModelOptional);

    ResponseEntity<Page<ClientModel>> getObjectResponseEntityAllClientes(Page<ClientModel> resultClients);
}
