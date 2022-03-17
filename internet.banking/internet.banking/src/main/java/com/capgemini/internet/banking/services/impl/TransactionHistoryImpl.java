package com.capgemini.internet.banking.services.impl;

import com.capgemini.internet.banking.models.TransactionHistoryModel;
import com.capgemini.internet.banking.repositories.TransactionHistoryRepository;
import com.capgemini.internet.banking.services.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionHistoryImpl implements TransactionHistoryService {

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

/*    @Override
    public List<TransactionHistoryModel> geAlltHistoryClients() {
        return transactionHistoryRepository.geAlltHistoryClients();
    }*/

    @Override
    public TransactionHistoryModel save(TransactionHistoryModel transactionHistoryModel) {
        return transactionHistoryRepository.save(transactionHistoryModel);
    }

/*    @Override
    public Optional<TransactionHistoryModel> findTransactionDate(LocalDateTime date) {
        return transactionHistoryRepository.findAllByDate();
    }*/
}
