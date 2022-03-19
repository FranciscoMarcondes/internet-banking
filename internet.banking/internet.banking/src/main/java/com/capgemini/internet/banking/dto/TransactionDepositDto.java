package com.capgemini.internet.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDepositDto {

    @NotNull(message = "the clientId field cannot be null")
    private Long clientId;

    @NotNull(message = "the deposit field cannot be null")
    @Min(value = 0, message = "invalid value, negative values are not allowed")
    private BigDecimal deposit;
}
