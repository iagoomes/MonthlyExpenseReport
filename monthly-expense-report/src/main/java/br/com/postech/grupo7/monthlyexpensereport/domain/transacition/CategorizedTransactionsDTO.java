package br.com.postech.grupo7.monthlyexpensereport.domain.transacition;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CategorizedTransactionsDTO {
    @JsonProperty("categorized_transactions")
    private List<TransactionDTO> categorizedTransactionDTOS;
}