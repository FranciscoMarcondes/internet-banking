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

    @NotNull
    private Long clientId;

    @NotNull
    @Min(value = 0)
    private BigDecimal deposit;
}
