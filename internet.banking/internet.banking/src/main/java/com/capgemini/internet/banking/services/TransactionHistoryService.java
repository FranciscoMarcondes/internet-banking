package com.capgemini.internet.banking.services;

import com.capgemini.internet.banking.enums.OperationType;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.models.TransactionHistoryModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionHistoryService {
    TransactionHistoryModel save(TransactionHistoryModel transactionHistoryModel);
    TransactionHistoryModel CreateNewHistory(BigDecimal value, ClientModel clientModelNewBalance, OperationType operationType);
    List<TransactionHistoryModel> getHistoryByDate(LocalDate initDate, LocalDate endDate);
    LocalDate validEndDate(LocalDate initDate, LocalDate endDate);
}
