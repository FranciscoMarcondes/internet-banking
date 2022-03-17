package com.capgemini.internet.banking.services;

import com.capgemini.internet.banking.dto.SimpleHistoryResponse;
import com.capgemini.internet.banking.dto.TransactionWithDraw;
import com.capgemini.internet.banking.enums.OperationType;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.models.TransactionHistoryModel;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionHistoryService {
    TransactionHistoryModel save(TransactionHistoryModel transactionHistoryModel);
    TransactionHistoryModel CreateNewHistory(BigDecimal value, ClientModel clientModelNewBalance, OperationType operationType);
    List<TransactionHistoryModel> getHistoryByDate(LocalDate initDate, LocalDate endDate);
    List<SimpleHistoryResponse> getHistoryDateSimple(LocalDate initDate, LocalDate endDate);
    LocalDate validEndDate(LocalDate initDate, LocalDate endDate);
}
