package com.capgemini.internet.banking.repositories;

import com.capgemini.internet.banking.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientModel, Long> {
}
