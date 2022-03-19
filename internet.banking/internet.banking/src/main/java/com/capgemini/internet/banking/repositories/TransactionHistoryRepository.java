package com.capgemini.internet.banking.repositories;

import com.capgemini.internet.banking.querys.DateQuery;
import com.capgemini.internet.banking.models.TransactionHistoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

public interface TransactionHistoryRepository extends JpaRepository <TransactionHistoryModel,Long>, JpaSpecificationExecutor<TransactionHistoryModel> {

    @Query(value= DateQuery.FIND_BY_DATE, nativeQuery = true)
    Page<TransactionHistoryModel> getHistoryByDate(@RequestParam("initDate") LocalDate initDate,
                                                   @Param("endDate") LocalDate endDate,
                                                   @Param("limit")Pageable pageable);
}
