package com.capgemini.internet.banking.services;
import com.capgemini.internet.banking.models.ClientModel;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<ClientModel> findAll();

    ClientModel save(ClientModel clientModel);

    Optional<ClientModel> findByid(Long clientId);

    ClientModel withdraw(Optional<ClientModel> resultClient, Long value);
}
