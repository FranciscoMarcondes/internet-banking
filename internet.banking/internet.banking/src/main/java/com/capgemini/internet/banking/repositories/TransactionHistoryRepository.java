package com.capgemini.internet.banking.repositories;

import com.capgemini.internet.banking.dto.SimpleHistoryResponse;
import com.capgemini.internet.banking.querys.DateQuery;
import com.capgemini.internet.banking.models.TransactionHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;


public interface TransactionHistoryRepository extends JpaRepository <TransactionHistoryModel,Long>, JpaSpecificationExecutor<TransactionHistoryModel> {

    @Query(value= DateQuery.FIND_BY_DATE, nativeQuery = true)
    List<TransactionHistoryModel> getHistoryByDate(@RequestParam("initDate") LocalDate initDate, @Param("endDate") LocalDate endDate);

    @Query(value= DateQuery.FIND_BY_DATE_SIMPLE, nativeQuery = true)
    List<SimpleHistoryResponse> getHistoryByDateSimple(@RequestParam("initDate") LocalDate initDate, @Param("endDate") LocalDate endDate);
}
