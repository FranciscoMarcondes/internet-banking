package com.capgemini.internet.banking.services;

import com.capgemini.internet.banking.enums.OperationType;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.models.TransactionHistoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionHistoryService {
    TransactionHistoryModel save(TransactionHistoryModel transactionHistoryModel);
    TransactionHistoryModel createNewHistory(BigDecimal value, ClientModel clientModelNewBalance, OperationType operationType);
    ResponseEntity<Page<TransactionHistoryModel>> getHistoryByDate(LocalDate initDate, LocalDate endDate, Pageable pageable);
}
