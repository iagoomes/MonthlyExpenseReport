package br.com.postech.grupo7.monthlyexpensereport.domain.transacition;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    @JsonProperty("date")
    private String date_transaction;
    private String description;
    @JsonProperty("value")
    private String value_trasaction;
    @JsonProperty("installment_current")
    private int installmentCurrent;
    @JsonProperty("installment_total")
    private int installmentTotal;
    private String category;

}
