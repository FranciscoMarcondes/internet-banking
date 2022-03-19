package com.capgemini.internet.banking.controllers;

import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.models.TransactionHistoryModel;
import com.capgemini.internet.banking.services.TransactionHistoryService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/history")
@Api(value = "Internet Banking - Transaction History")
@Tag(name = "Transaction Service", description = "API for handling Transaction data")
public class TransactionHistoryController {

    @Autowired
    TransactionHistoryService historyService;

    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal serve error"),
            @ApiResponse(code = 200, message = "Ok")
    })
    @ApiOperation(value = "historical search by date.")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Page<TransactionHistoryModel>> getHistoryDate(
            @PageableDefault(page = 0, size = 10, sort = "TRANSACTION_DATE",
                    direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam("initDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate initDate,
            @Param("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        log.debug("GET, getHistoryDate() ");
        log.info("searching all histories by date");
        if (endDate == null) {
            endDate = LocalDate.now();
        } else if (endDate.isBefore(initDate)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return historyService.getHistoryByDate(initDate, endDate, pageable);
    }
}
