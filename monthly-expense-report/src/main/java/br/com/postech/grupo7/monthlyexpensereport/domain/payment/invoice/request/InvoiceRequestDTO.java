package br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceRequestDTO {
    @NotNull
    private Integer customerId;
    @NotNull
    private Integer archiveId;
    private String analysisResults;
}
