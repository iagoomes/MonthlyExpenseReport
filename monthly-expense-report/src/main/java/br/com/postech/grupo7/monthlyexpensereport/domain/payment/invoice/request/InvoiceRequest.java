package br.com.postech.grupo7.monthlyexpensereport.domain.payment.invoice.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "invoice_request")
public class InvoiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "status", nullable = false, length = 50)
    private String status = "Pending";

    @Column(name = "submitted_at", nullable = false)
    private LocalDate submittedAt;

    @Column(name = "analysis_results")
    private String analysisResults;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "token_count", nullable = false)
    private Integer tokenCount = 0;

    public InvoiceRequest(final String analysisResults, final Customer customer, final Integer tokenCount) {
        this.customer = customer;
        this.analysisResults = this.analysisResults;// invoiceRequestDTO.getAnalysisResults() == null ? null :
                                                    // invoiceRequestDTO.getAnalysisResults();
        this.submittedAt = LocalDate.now();
        this.tokenCount = tokenCount;
    }

}