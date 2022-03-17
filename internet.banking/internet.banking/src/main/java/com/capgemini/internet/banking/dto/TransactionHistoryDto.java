package com.capgemini.internet.banking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionHistoryDto {
    private Long transactionId;
    private Long clientId;
    private BigDecimal withdraw;
    private BigDecimal deposit;
}
