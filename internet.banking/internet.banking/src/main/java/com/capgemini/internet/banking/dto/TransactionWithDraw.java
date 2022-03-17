package com.capgemini.internet.banking.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransactionWithDraw {

    @NotNull
    private Long clientId;
    @NotNull
    private BigDecimal withDraw;
}
