package com.capgemini.internet.banking.controllers;

import com.capgemini.internet.banking.dto.ClientDto;
import com.capgemini.internet.banking.dto.TransactionDeposit;
import com.capgemini.internet.banking.dto.TransactionWithDraw;
import com.capgemini.internet.banking.enums.OperationType;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.services.ClientService;
import com.capgemini.internet.banking.services.TransactionHistoryService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/client")
@Api(value = "Internet Banking - Client function")
@Tag(name = "Client Service", description = "API for handling customer data")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    TransactionHistoryService transactionHistoryService;

    @ApiOperation(value = "find All clients.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 200, message = "Ok"),
    })
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllClients() {
        log.debug("Get, getAllClients, looking for all clients {}");
        List<ClientModel> resultClients = clientService.findAll();
        return clientService.getObjectResponseEntityAllClientes(resultClients);
    }

    @ApiOperation(value = "find one client by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 200, message = "Ok")
    })
    @RequestMapping(value = "/{clientId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getOneClient(@PathVariable(value = "clientId") Long clientId){
        log.debug("Get, getOneClient, looking for one client {}");
        Optional<ClientModel> clientModelOptional = clientService.findByid(clientId);
        return clientService.validAndGetResponseEntityOneClient(clientModelOptional);
    }

    @ApiOperation(value = "Create client.")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error"),
            @ApiResponse(code = 201, message = "Created")
    })
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

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiOperation(value = "withdraw function.")
    @RequestMapping(path = "/withdraw", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> withdraw(@RequestBody @Valid TransactionWithDraw transaction){

        log.debug("POST withdraw client {} ", transaction.getClientId());
        log.info("Checking client ", transaction.getClientId());

        Optional<ClientModel> resultClient = clientService.findByid(transaction.getClientId());
        clientService.validateRulesWithDraw(transaction, resultClient);

        var clientModelNewBalance = clientService.withdraw(resultClient, transaction.getWithDraw());
        clientService.save(clientModelNewBalance);
        var TransactionHistory =transactionHistoryService.CreateNewHistory(transaction.getWithDraw(), clientModelNewBalance, OperationType.WITHDRAW);

        log.debug("PUT withdraw balance updated successfully {}" , TransactionHistory.getTransactionId());
        log.info("balance updated successfully {}", TransactionHistory.getTransactionId());
        return ResponseEntity.status(HttpStatus.OK).body("finished withdrawal");
    }

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiOperation(value = "deposit function." )
    @RequestMapping(path = "/deposit", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> deposit(@RequestBody @Valid TransactionDeposit transaction){

        log.debug("POST deposit client {} ", transaction.getClientId());
        log.info("Checking client ", transaction.getClientId());

        Optional<ClientModel> resultClient = clientService.findByid(transaction.getClientId());
        clientService.validateRulesDeposit(transaction, resultClient);

        var clientModelNewBalance = clientService.deposit(resultClient, transaction.getDeposit());
        clientService.save(clientModelNewBalance);
        var TransactionHistory = transactionHistoryService.CreateNewHistory(transaction.getDeposit(), clientModelNewBalance, OperationType.DEPOSIT);

        log.debug("PUT deposit balance updated successfully {}" , TransactionHistory.getTransactionId());
        log.info("balance updated successfully {}", TransactionHistory.getTransactionId());
        return ResponseEntity.status(HttpStatus.OK).body("balance updated successfully");
    }

}
