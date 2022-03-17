package com.capgemini.internet.banking.controllers;

import com.capgemini.internet.banking.dto.ClientDto;
import com.capgemini.internet.banking.dto.TransactionDeposit;
import com.capgemini.internet.banking.dto.TransactionHistoryDto;
import com.capgemini.internet.banking.dto.TransactionWithDraw;
import com.capgemini.internet.banking.enums.OperationType;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.models.TransactionHistoryModel;
import com.capgemini.internet.banking.services.ClientService;
import com.capgemini.internet.banking.services.TransactionHistoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/client")
@Tag(name = "Client Service", description = "API for handling customer data")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    TransactionHistoryService transactionHistoryService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllClients() {
        List<ClientModel> resultClients = clientService.findAll();
        if (resultClients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Clients not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(resultClients);
    }

    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getOneClient(@PathVariable(value = "clientId") Long clientId){
        Optional<ClientModel> clientModelOptional = clientService.findByid(clientId);
        if(!clientModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(clientModelOptional.get());
        }
    }


    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> saveClient(@RequestBody @Valid ClientDto clientDto){
        log.debug("POST saveClient clientDto received {} ", clientDto.toString());
        var clientModel = new ClientModel();
        BeanUtils.copyProperties(clientDto, clientModel);
        clientService.save(clientModel);
        log.debug("POST saveClient clientId save {}" , clientModel.getClientId());
        log.info("Client saved successfully cliendId {}", clientModel.getClientId());
        return ResponseEntity.status(HttpStatus.CREATED).body(clientModel);
    }

    @RequestMapping(path = "/withdraw", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> withdraw(@RequestBody @Valid TransactionWithDraw transaction){

        log.debug("POST withdraw client {} ", transaction.getClientId());
        log.info("Checking client ", transaction.getClientId());

        Optional<ClientModel> resultClient = clientService.findByid(transaction.getClientId());
        clientService.validateRulesWithDraw(transaction, resultClient);

        var clientModelNewBalance = clientService.withdraw(resultClient, transaction.getWithDraw());
        var resultClientModel = clientService.save(clientModelNewBalance);
        var TransactionHistory =transactionHistoryService.CreateNewHistory(transaction.getWithDraw(), clientModelNewBalance, OperationType.WITHDRAW);

        log.debug("PUT withdraw balance updated successfully {}" , TransactionHistory.getTransactionId());
        log.info("balance updated successfully {}", TransactionHistory.getTransactionId());
        return ResponseEntity.status(HttpStatus.CREATED).body(resultClientModel);
    }

    @RequestMapping(path = "/deposit", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> deposit(@RequestBody @Valid TransactionDeposit transaction){

        log.debug("POST deposit client {} ", transaction.getClientId());
        log.info("Checking client ", transaction.getClientId());

        Optional<ClientModel> resultClient = clientService.findByid(transaction.getClientId());
        clientService.validateRulesDeposit(transaction, resultClient);

        var clientModelNewBalance = clientService.deposit(resultClient, transaction.getDeposit());
        var resultClientModel = clientService.save(clientModelNewBalance);
        var TransactionHistory = transactionHistoryService.CreateNewHistory(transaction.getDeposit(), clientModelNewBalance, OperationType.DEPOSIT);

        log.debug("PUT deposit balance updated successfully {}" , TransactionHistory.getTransactionId());
        log.info("balance updated successfully {}", TransactionHistory.getTransactionId());
        return ResponseEntity.status(HttpStatus.CREATED).body(resultClientModel);
    }

}
