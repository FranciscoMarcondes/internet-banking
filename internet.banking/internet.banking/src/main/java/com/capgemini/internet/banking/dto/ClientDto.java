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

    @NotBlank(message = "the name field cannot be empty")
    @Size(min = 3, max = 100, message = "The field must be at least 3 to 100 characters")
    private String name;

    @NotNull(message = "the exclusivePlan must be true or false")
    private Boolean exclusivePlan;

    @NotNull(message = "the balance field cannot be null")
    @Min(value = 0, message = "invalid value, negative values are not allowed")
    private BigDecimal balance;

    @NotBlank(message = "the account field cannot be empty")
    @Size(min = 3, max = 7, message = "The account field must contain between 3 and 7 characters")
    private String account;

    @NotNull(message = "the birthData field cannot be null")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthData;
}
