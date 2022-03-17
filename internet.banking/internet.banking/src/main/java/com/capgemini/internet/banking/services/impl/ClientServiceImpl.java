package com.capgemini.internet.banking.services.impl;

import com.capgemini.internet.banking.dto.TransactionDeposit;
import com.capgemini.internet.banking.dto.TransactionWithDraw;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.repositories.ClientRepository;
import com.capgemini.internet.banking.services.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public ClientModel save(ClientModel clientModel) {
        return clientRepository.save(clientModel);
    }

    @Override
    public Optional<ClientModel> findByid(Long clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public ClientModel withdraw(Optional<ClientModel> resultClient, BigDecimal value) {

        // verifica se é Isento de taxa de saque
        if (resultClient.get().getExclusivePlan() || value.compareTo(BigDecimal.valueOf(100)) <= 0) {
            BigDecimal newBalance = resultClient.get().getBalance().subtract(value);
            return getNewClientModel(resultClient, newBalance);

        }
        // Não é inseto, verica qual taxa se enquadra
        else if (value.compareTo(BigDecimal.valueOf(100)) > 0 && value.compareTo(BigDecimal.valueOf(300)) <= 0) {
            var tax = value.multiply(BigDecimal.valueOf(0.4));
            var sum = value.add(tax);
            BigDecimal newBalance = resultClient.get().getBalance().subtract(sum);
            return getNewClientModel(resultClient, newBalance);
        }

        // Valor acima de 300 taxa de 1%
        var tax = value.multiply(BigDecimal.valueOf(0.1));
        var sum = value.add(tax);
        BigDecimal newBalance = resultClient.get().getBalance().subtract(sum);
        return getNewClientModel(resultClient, newBalance);
    }

    private ClientModel getNewClientModel(Optional<ClientModel> resultClient, BigDecimal newBalance) {
        var clientModel = new ClientModel();
        BeanUtils.copyProperties(resultClient.get(), clientModel);
        clientModel.setBalance(newBalance);
        return clientModel;
    }

    @Override
    public ClientModel deposit(Optional<ClientModel> resultClient, BigDecimal deposit) {
        BigDecimal newBalance = resultClient.get().getBalance().add(deposit);
        return getNewClientModel(resultClient, newBalance);
    }

    @Override
    public ResponseEntity<Object> validAndGetResponseEntityOneClient(Optional<ClientModel> clientModelOptional) {
        if(!clientModelOptional.isPresent()){
            log.info("Client not found {}");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(clientModelOptional.get());
        }
    }

    @Override
    public ResponseEntity<Object> getObjectResponseEntityAllClientes(List<ClientModel> resultClients) {
            if (resultClients.isEmpty()) {
                log.info("Clients not found {}");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clients not found.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(resultClients);
    }

    @Override
    public ResponseEntity<Object> validateRulesWithDraw(TransactionWithDraw transaction, Optional<ClientModel> resultClient) {
        if (!resultClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        }
        if (transaction.getWithDraw().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect past value.");
        }
        if (transaction.getWithDraw().compareTo(resultClient.get().getBalance()) > 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("insufficient funds. balance available {} "
                    + resultClient.get().getBalance());
        }
        return null;
    }

    @Override
    public ResponseEntity<Object> validateRulesDeposit(TransactionDeposit transaction, Optional<ClientModel> resultClient) {
        if (!resultClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found.");
        }
        if (transaction.getDeposit().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect past value.");
        }
        return null;
    }

}
