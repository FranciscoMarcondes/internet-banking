package com.capgemini.internet.banking.service;

import com.capgemini.internet.banking.dto.TransactionDepositDto;
import com.capgemini.internet.banking.dto.TransactionWithDrawDto;
import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.repositories.ClientRepository;
import com.capgemini.internet.banking.services.ClientService;
import com.capgemini.internet.banking.services.impl.ClientServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

    @Mock
    ClientRepository clientRepository;

    @InjectMocks
    ClientServiceImpl clientService;

    @Mock
    Pageable pageable;

    @Before("")
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest (){
/*        List<ClientModel> list = new ArrayList<>();
        ClientModel model = new ClientModel();
        list.add(model);


        when(clientService.findAll(pageable)).thenReturn();
        result = (List<ClientModel>) clientService.findAll();
        assertEquals(result.size(), list.size());*/
    }

    @Test
    public void findByidTest(){
/*        ClientModel model = new ClientModel();
        model.setClientId(1L);
        Optional<ClientModel> optional = Optional.of(model);
        when(clientService.findByid(eq(1L))).thenReturn(optional);
        Optional<ClientModel> result = clientService.findByid(model.getClientId());
        assertEquals(result.get().getClientId(), model.getClientId());*/
    }

    @Test
    public void saveTest(){
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setBalance(new BigDecimal(100));
        model.setExclusivePlan(true);
        model.setBirthData(LocalDate.now());
        model.setName("teste");

        when(clientService.save(model)).thenReturn(model);
        ClientModel result = clientService.save(model);
        assertEquals(result.getClientId(), model.getClientId());
        assertEquals(result.getBalance(), model.getBalance());
        assertEquals(result.getExclusivePlan(), model.getExclusivePlan());
        assertEquals(result.getBirthData(), model.getBirthData());
        assertEquals(result.getName(), model.getName());
    }
    @Test
    public void withdrawExclusivePlanTest (){
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setBalance(new BigDecimal(500));
        model.setAccount("12345");
        model.setBirthData(LocalDate.now());
        model.setName("teste");
        model.setExclusivePlan(true);

        Optional<ClientModel> optional = Optional.of(model);
        ClientModel result = clientService.withdraw(optional, new BigDecimal(100));
        assertEquals(result.getBalance(),new BigDecimal(400 ));
    }

    @Test
    public void withdrawNoExclusivePlanTest (){
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setBalance(new BigDecimal(500));
        model.setAccount("12345");
        model.setBirthData(LocalDate.now());
        model.setName("teste");
        model.setExclusivePlan(false);

        Optional<ClientModel> optional = Optional.of(model);
        ClientModel result = clientService.withdraw(optional, new BigDecimal(100));
        assertEquals(result.getBalance(),new BigDecimal(400 ));
    }

    @Test
    public void withdrawType2Test (){
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setBalance(new BigDecimal(500));
        model.setAccount("12345");
        model.setBirthData(LocalDate.now());
        model.setName("teste");
        model.setExclusivePlan(false);

        Optional<ClientModel> optional = Optional.of(model);
        ClientModel result = clientService.withdraw(optional, new BigDecimal(200));
        assertTrue(result.getBalance().compareTo(new BigDecimal(220)) == 0);
    }

    @Test
    public void withdrawType3Test (){
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setBalance(new BigDecimal(500));
        model.setAccount("12345");
        model.setBirthData(LocalDate.now());
        model.setName("teste");
        model.setExclusivePlan(false);
        Optional<ClientModel> optional = Optional.of(model);
        ClientModel result = clientService.withdraw(optional, new BigDecimal(400));
        assertTrue(result.getBalance().compareTo(new BigDecimal(60)) == 0);
    }

    @Test
    public void depositTest (){
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setBalance(new BigDecimal(500));
        model.setAccount("12345");
        model.setBirthData(LocalDate.now());
        model.setName("teste");
        model.setExclusivePlan(false);

        Optional<ClientModel> optional = Optional.of(model);
        ClientModel result = clientService.deposit(optional, new BigDecimal(400));
        assertTrue(result.getBalance().compareTo(new BigDecimal(900)) == 0);
    }

    @Test
    public void validateRulesWithDrawTest (){
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setBalance(new BigDecimal(500));
        model.setAccount("12345");
        model.setBirthData(LocalDate.now());
        model.setName("teste");
        model.setExclusivePlan(false);
        Optional<ClientModel> optional = Optional.of(model);
        TransactionWithDrawDto dto = new TransactionWithDrawDto();
        dto.setClientId(1L);
        dto.setWithDraw(BigDecimal.ZERO);

        ResponseEntity<Object> result = clientService.validateRulesWithDraw(dto, optional);
        assertEquals(result.getStatusCode().value(), 400);

        dto.setWithDraw(new BigDecimal(700));
        assertEquals(result.getStatusCode().value(), 400);
    }

    @Test
    public void validateRulesWithDrawNullTeste (){
        TransactionWithDrawDto dto = new TransactionWithDrawDto();
        Optional<ClientModel> optional = Optional.empty();
        dto.setClientId(1L);
        dto.setWithDraw(BigDecimal.ZERO);
        ResponseEntity<Object> result = clientService.validateRulesWithDraw(dto, optional);
        assertEquals(result.getStatusCode().value(), 404);
    }

    @Test
    public void validateRulesDepositNullTest (){
        TransactionDepositDto dto = new TransactionDepositDto();
        Optional<ClientModel> optional = Optional.empty();
        dto.setClientId(1L);
        dto.setDeposit(BigDecimal.ZERO);
        ResponseEntity<Object> result = clientService.validateRulesDeposit(dto, optional);
        assertEquals(result.getStatusCode().value(), 404);
    }

    @Test
    public void validateRulesDepositLessThanZeroTest(){
        ClientModel model = new ClientModel();
        model.setClientId(1L);
        model.setBalance(new BigDecimal(500));
        model.setAccount("12345");
        model.setBirthData(LocalDate.now());
        model.setName("teste");
        model.setExclusivePlan(false);
        TransactionDepositDto dto = new TransactionDepositDto();
        Optional<ClientModel> optional = Optional.of(model);
        dto.setClientId(1L);
        dto.setDeposit(new BigDecimal(-1));
        ResponseEntity<Object> result = clientService.validateRulesDeposit(dto, optional);
        assertEquals(result.getStatusCode().value(), 400);
    }
}
