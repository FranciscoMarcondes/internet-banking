package com.capgemini.internet.banking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleHistoryResponse {
    private BigDecimal withdraw;
    private BigDecimal deposit;
}
