package com.capgemini.internet.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDeposit {

    @NotNull
    private Long clientId;
    @NotNull
    private BigDecimal deposit;
}
