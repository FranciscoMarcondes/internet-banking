package com.capgemini.internet.banking.services.impl;

import com.capgemini.internet.banking.enums.OperationType;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.models.TransactionHistoryModel;
import com.capgemini.internet.banking.repositories.TransactionHistoryRepository;
import com.capgemini.internet.banking.services.TransactionHistoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Log4j2
@Service
public class TransactionHistoryImpl implements TransactionHistoryService {

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public TransactionHistoryModel save(TransactionHistoryModel transactionHistoryModel) {
        return transactionHistoryRepository.save(transactionHistoryModel);
    }

    public TransactionHistoryModel createNewHistory(BigDecimal value, ClientModel clientModelNewBalance, OperationType operationType) {

        var history = new TransactionHistoryModel();
        history.setBalance(clientModelNewBalance.getBalance());

        if (operationType.equals(OperationType.WITHDRAW)) {
            history.setWithdraw(value);
            history.setDeposit(BigDecimal.ZERO);
        } else {
            history.setWithdraw(BigDecimal.ZERO);
            history.setDeposit(value);
        }
        history.setTransactionDate(LocalDate.now());
        history.setClient(clientModelNewBalance);
        return save(history);
    }

    @Override
    public ResponseEntity<Page<TransactionHistoryModel>> getHistoryByDate(LocalDate initDate, LocalDate endDate, Pageable pageable) {
        try {
            Page<TransactionHistoryModel> resultHistory = transactionHistoryRepository.getHistoryByDate(initDate, endDate, pageable);
            if (resultHistory.isEmpty()) {
                log.info("History not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultHistory);
            }
            log.info("search ended");
            return ResponseEntity.status(HttpStatus.OK).body(resultHistory);

        } catch (Exception e) {
            log.debug("getHistoryByDate () - Something unexpected happened.");
            log.info("Something unexpected happened.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
