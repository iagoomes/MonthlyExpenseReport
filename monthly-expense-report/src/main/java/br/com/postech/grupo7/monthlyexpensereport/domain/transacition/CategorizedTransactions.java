package br.com.postech.grupo7.monthlyexpensereport.domain.transacition;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class CategorizedTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "categorizedTransactions", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transactions> transactions;

    public CategorizedTransactions(List<Transactions> categorizedTransactions) {
        this.transactions = categorizedTransactions;
    }
}
