package com.capgemini.internet.banking.repositories;

import com.capgemini.internet.banking.models.TransactionHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TransactionHistoryRepository extends JpaRepository <TransactionHistoryModel,Long>, JpaSpecificationExecutor<TransactionHistoryModel> {

    /*List<TransactionHistoryModel> geAlltHistoryClients();*/

    /*Optional<TransactionHistoryModel> findAllByDate();*/

}
