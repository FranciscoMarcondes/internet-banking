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
public class TransactionWithDrawDto {

    @NotNull(message = "the clientId field cannot be null")
    private Long clientId;

    @NotNull(message = "the withDraw field cannot be null")
    @Min(value = 0)
    private BigDecimal withDraw;
}
