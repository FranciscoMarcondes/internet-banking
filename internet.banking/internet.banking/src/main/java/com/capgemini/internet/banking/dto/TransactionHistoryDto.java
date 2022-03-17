package com.capgemini.internet.banking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionHistoryDto {
    private Long transactionId;
    private BigDecimal withdraw;
    private BigDecimal deposit;
    private BigDecimal balance;
}
