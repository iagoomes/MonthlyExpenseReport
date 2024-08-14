package br.com.postech.grupo7.monthlyexpensereport.domain.transacition;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String date_transaction;
    private String description;
    private String value_transaction;
    private int installmentCurrent;
    private int installmentTotal;
    private String category;
    @ManyToOne
    @JoinColumn(name = "categorized_transactions_id")
    private CategorizedTransactions categorizedTransactions;

    public Transactions(TransactionDTO dto){
        this.date_transaction = dto.getDate_transaction();
        this.description = dto.getDescription();
        this.value_transaction = dto.getValue_trasaction();
        this.installmentCurrent = dto.getInstallmentCurrent();
        this.installmentTotal = dto.getInstallmentTotal();
        this.category = dto.getCategory();
    }
}