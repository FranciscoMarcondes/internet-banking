package com.capgemini.internet.banking.models;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_TRANSACTION")
public class TransactionHistoryModel implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transactionId", nullable = false)
    private Long transactionId;

    @Column
    private BigDecimal withdraw;

    @Column
    private BigDecimal deposit;

    @Column
    private BigDecimal balance;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate transactionDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ClientModel client;

}
