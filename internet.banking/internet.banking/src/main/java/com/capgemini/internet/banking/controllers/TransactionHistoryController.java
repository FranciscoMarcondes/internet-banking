package com.capgemini.internet.banking.controllers;

import com.capgemini.internet.banking.models.TransactionHistoryModel;
import com.capgemini.internet.banking.services.TransactionHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/history")
@Tag(name = "Transaction Service", description = "API for handling Transaction data")
public class TransactionHistoryController {

    @Autowired
    TransactionHistoryService historyService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getHistoryDate(@RequestParam("initDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate initDate,
                                                 @Param("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        endDate = historyService.validEndDate(initDate, endDate);
        List<TransactionHistoryModel> resultHistory = historyService.getHistoryByDate(initDate, endDate);
        if (resultHistory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("History not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(resultHistory);
    }

/*    @PostMapping
    public ResponseEntity<Object> saveClient(@RequestBody @Valid TransactionHistoryDto historyDto){
        log.debug("POST saveClient clientDto received {} ", historyDto.toString());
        var historyModel = new TransactionHistoryModel();
        BeanUtils.copyProperties(historyDto, historyModel);
        historyModel.setTransactionDate(LocalDateTime.now());
        historyService.save(historyModel);
        log.debug("POST saveClient clientId save {}" , historyModel.getTransactionId());
        log.info("Client saved successfully cliendId {}", historyModel.getTransactionId());
        return ResponseEntity.status(HttpStatus.CREATED).body(historyModel);
    }*/
}
