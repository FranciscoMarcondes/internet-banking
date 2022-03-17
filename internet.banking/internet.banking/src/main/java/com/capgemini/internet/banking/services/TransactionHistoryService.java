package com.capgemini.internet.banking.services;

import com.capgemini.internet.banking.models.TransactionHistoryModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionHistoryService {

/*    List<TransactionHistoryModel> geAlltHistoryClients();*/

    TransactionHistoryModel save(TransactionHistoryModel transactionHistoryModel);

    /*Optional<TransactionHistoryModel> findTransactionDate(LocalDateTime date);*/
}
