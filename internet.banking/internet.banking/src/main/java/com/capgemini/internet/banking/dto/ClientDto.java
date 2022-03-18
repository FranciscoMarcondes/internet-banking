package com.capgemini.internet.banking.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {

    private Long clientId;

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    private Boolean exclusivePlan;

    @NotNull
    @Min(value = 0)
    private BigDecimal balance;

    @NotBlank
    @Size(min = 3, max = 7)
    private String account;

    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthData;
}
