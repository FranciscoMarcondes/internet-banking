package com.capgemini.internet.banking.services.impl;

import com.capgemini.internet.banking.dto.SimpleHistoryResponse;
import com.capgemini.internet.banking.enums.OperationType;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.models.TransactionHistoryModel;
import com.capgemini.internet.banking.repositories.TransactionHistoryRepository;
import com.capgemini.internet.banking.services.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionHistoryImpl implements TransactionHistoryService {

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public TransactionHistoryModel save(TransactionHistoryModel transactionHistoryModel) {
        return transactionHistoryRepository.save(transactionHistoryModel);
    }

    public TransactionHistoryModel CreateNewHistory(BigDecimal value, ClientModel clientModelNewBalance, OperationType operationType) {
        var history = new TransactionHistoryModel();
        history.setBalance(clientModelNewBalance.getBalance());

        // Verifica se é saque caso não intendo que é um deposito.
        if (operationType.equals(OperationType.WITHDRAW)) {
            history.setWithdraw(value);
            history.setDeposit(BigDecimal.ZERO);
        } else {
            history.setWithdraw(BigDecimal.ZERO);
            history.setDeposit(value);
        }
        history.setTransactionDate(LocalDateTime.now());
        history.setClient(clientModelNewBalance);
        return save(history);
    }

    @Override
    public List<TransactionHistoryModel> getHistoryByDate(LocalDate initDate, LocalDate endDate) {
        return transactionHistoryRepository.getHistoryByDate(initDate, endDate);
    }

    @Override
    public List<SimpleHistoryResponse> getHistoryDateSimple(LocalDate initDate, LocalDate endDate) {
        return transactionHistoryRepository.getHistoryByDateSimple(initDate, endDate);
    }

    @Override
    public LocalDate validEndDate(LocalDate initDate, LocalDate endDate) {
        if (endDate == null) {
            endDate = initDate.plusDays(1);
        }
        return endDate;
    }
}
