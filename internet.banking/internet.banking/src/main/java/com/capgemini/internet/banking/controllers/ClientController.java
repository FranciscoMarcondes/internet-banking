package com.capgemini.internet.banking.controllers;

import com.capgemini.internet.banking.dto.ClientDto;
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

    @RequestMapping(value = "/{value}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> withdraw(@PathVariable(value = "clientId") Long clientId,
                                           @PathVariable(value = "value") Long value){
        log.debug("POST withdraw client {} ", clientId);
        Optional<ClientModel> resultClient = clientService.findByid(clientId);
        if(!resultClient.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }

        var clientModel = new ClientModel();
        var transaction = new TransactionHistoryModel();
        BeanUtils.copyProperties(resultClient, clientModel);
        ClientModel clientModelNewBalance = clientService.withdraw(resultClient, value);

        // Criando um novo historico
        transaction.setClient(clientModelNewBalance);
        transaction.setWithdraw(BigDecimal.valueOf(value));
        transaction.setDeposit(BigDecimal.ZERO);
        transaction.setTransactionDate(LocalDateTime.now());

        clientService.save(clientModelNewBalance);
        transactionHistoryService.save(transaction);  //TODO Corrir metodo.

        log.debug("POST saveClient clientId save {}" , clientModel.getClientId());
        log.info("Client saved successfully cliendId {}", clientModel.getClientId());
        return ResponseEntity.status(HttpStatus.CREATED).body(clientModel);
    }


}
