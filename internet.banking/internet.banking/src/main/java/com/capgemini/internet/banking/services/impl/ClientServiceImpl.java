package com.capgemini.internet.banking.services.impl;

import com.capgemini.internet.banking.models.ClientModel;
import com.capgemini.internet.banking.repositories.ClientRepository;
import com.capgemini.internet.banking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientModel> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public ClientModel save(ClientModel clientModel) {
        return clientRepository.save(clientModel);
    }

    @Override
    public Optional<ClientModel> findByid(Long clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public ClientModel withdraw(Optional<ClientModel> resultClient, Long value) {
        BigDecimal newBalance = resultClient.get().getBalance().subtract(new BigDecimal(value));
        var clientModel = new ClientModel();
        clientModel.setClientId(resultClient.get().getClientId());
        clientModel.setAccount(resultClient.get().getAccount());
        clientModel.setBalance(newBalance);
        clientModel.setBirthData(resultClient.get().getBirthData());
        clientModel.setName(resultClient.get().getName());
        clientModel.setExclusivePlan(resultClient.get().getExclusivePlan());
        clientModel.setTransactionHistoryList(resultClient.get().getTransactionHistoryList());
        return clientModel;
    }
}
