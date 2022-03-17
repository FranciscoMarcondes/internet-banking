package com.capgemini.internet.banking.services;
import com.capgemini.internet.banking.dto.TransactionDeposit;
import com.capgemini.internet.banking.dto.TransactionWithDraw;
import com.capgemini.internet.banking.models.ClientModel;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<ClientModel> findAll();

    ClientModel save(ClientModel clientModel);

    Optional<ClientModel> findByid(Long clientId);

    ClientModel withdraw(Optional<ClientModel> resultClient, BigDecimal value);

    ResponseEntity<Object> validateRulesWithDraw(TransactionWithDraw transaction, Optional<ClientModel> resultClient);

    ResponseEntity<Object>  validateRulesDeposit(TransactionDeposit transaction, Optional<ClientModel> resultClient);

    ClientModel deposit(Optional<ClientModel> resultClient, BigDecimal deposit);

    ResponseEntity<Object> validAndGetResponseEntityOneClient(Optional<ClientModel> clientModelOptional);

    ResponseEntity<Object> getObjectResponseEntityAllClientes(List<ClientModel> resultClients);
}
