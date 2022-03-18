package com.capgemini.internet.banking.controllers;

import com.capgemini.internet.banking.dto.ClientDto;
import com.capgemini.internet.banking.dto.TransactionDepositDto;
import com.capgemini.internet.banking.dto.TransactionWithDrawDto;
import com.capgemini.internet.banking.enums.OperationType;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.services.ClientService;
import com.capgemini.internet.banking.services.TransactionHistoryService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.hibernate.HibernateException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<ClientModel>> getAllClients( @PageableDefault(page = 0, size = 10, sort = "clientId",
            direction = Sort.Direction.ASC) Pageable pageable) {

        log.debug("Get, getAllClients, looking for all clients {}");
        Page<ClientModel> resultClients = null;
        resultClients = clientService.findAll(pageable);
        return  clientService.getObjectResponseEntityAllClientes(resultClients);
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

        try {
            clientService.save(clientModel);
            log.debug("POST saveClient clientId save {}", clientModel.getClientId());
            log.info("Client saved successfully cliendId {}", clientModel.getClientId());
            return ResponseEntity.status(HttpStatus.CREATED).body(clientModel);
        } catch (HibernateException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering a new customer");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unexpected error");
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiOperation(value = "withdraw function.")
    @RequestMapping(path = "/withdraw", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> withdraw(@RequestBody @Valid TransactionWithDrawDto transaction) {
        try {
            log.debug("POST withdraw client {} ", transaction.getClientId());
            log.info("Checking client ", transaction.getClientId());

            Optional<ClientModel> resultClient = clientService.findByid(transaction.getClientId());
            var validateRulesWithDraw = clientService.validateRulesWithDraw(transaction, resultClient);
            if ( validateRulesWithDraw != null ){return validateRulesWithDraw;}

            var clientModelNewBalance = clientService.withdraw(resultClient, transaction.getWithDraw());
            clientService.save(clientModelNewBalance);

            var TransactionHistory = transactionHistoryService.CreateNewHistory(transaction.getWithDraw(), clientModelNewBalance, OperationType.WITHDRAW);
            log.debug("PUT withdraw balance updated successfully {}", TransactionHistory.getTransactionId());
            log.info("balance updated successfully {}", TransactionHistory.getTransactionId());
            return ResponseEntity.status(HttpStatus.OK).body("finished withdrawal");

        } catch (HibernateException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering a new customer");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unexpected error");
        }
    }

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiOperation(value = "deposit function." )
    @RequestMapping(path = "/deposit", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> deposit(@RequestBody @Valid TransactionDepositDto transaction){

        log.debug("POST deposit client {} ", transaction.getClientId());
        log.info("Checking client ", transaction.getClientId());

        Optional<ClientModel> resultClient = clientService.findByid(transaction.getClientId());

        var validateRulesDeposit = clientService.validateRulesDeposit(transaction, resultClient);
        if (validateRulesDeposit != null){return validateRulesDeposit;}

        var clientModelNewBalance = clientService.deposit(resultClient, transaction.getDeposit());

        try {
            clientService.save(clientModelNewBalance);
            var TransactionHistory = transactionHistoryService.CreateNewHistory(transaction.getDeposit(), clientModelNewBalance, OperationType.DEPOSIT);
            log.debug("PUT deposit balance updated successfully {}" , TransactionHistory.getTransactionId());
            log.info("balance updated successfully {}", TransactionHistory.getTransactionId());
            return ResponseEntity.status(HttpStatus.OK).body("balance updated successfully");
        } catch (HibernateException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering a new customer");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unexpected error");
        }
    }
}
