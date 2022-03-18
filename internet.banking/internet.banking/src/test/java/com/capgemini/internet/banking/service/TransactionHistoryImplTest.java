package com.capgemini.internet.banking.service;

import com.capgemini.internet.banking.enums.OperationType;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.models.TransactionHistoryModel;
import com.capgemini.internet.banking.repositories.TransactionHistoryRepository;
import com.capgemini.internet.banking.services.impl.TransactionHistoryImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionHistoryImplTest {

    @Mock
    TransactionHistoryRepository repository;

    @InjectMocks
    TransactionHistoryImpl service;

    @Mock
    Pageable pageable;

    @Before("")
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveTest(){
        ClientModel clientModel = new ClientModel();
        TransactionHistoryModel model = new TransactionHistoryModel();
        model.setBalance(BigDecimal.TEN);
        model.setClient(clientModel);
        model.setDeposit(new BigDecimal(200));
        model.setWithdraw(BigDecimal.ZERO);

        when(service.save(model)).thenReturn(model);
        TransactionHistoryModel result = service.save(model);
        assertEquals(result.getBalance(), model.getBalance());
        assertEquals(result.getDeposit(), model.getDeposit());
        assertEquals(result.getWithdraw(), model.getWithdraw());

    }

    @Test
    public void CreateNewHistory(){
        ClientModel clientModel = new ClientModel();
        TransactionHistoryModel model = new TransactionHistoryModel();
        model.setBalance(BigDecimal.TEN);
        model.setClient(clientModel);
        model.setDeposit(new BigDecimal(200));
        model.setWithdraw(BigDecimal.ZERO);

        when(service.save(model)).thenReturn(model);
        when(service.CreateNewHistory(new BigDecimal(100), clientModel, OperationType.WITHDRAW)).thenReturn(model);

        TransactionHistoryModel result = service.CreateNewHistory(new BigDecimal(100), clientModel, OperationType.WITHDRAW);
        assertEquals(result.getWithdraw(), new BigDecimal(100));
    }

}
