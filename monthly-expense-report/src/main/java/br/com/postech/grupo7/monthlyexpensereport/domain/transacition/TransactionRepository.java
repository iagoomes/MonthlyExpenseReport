package br.com.postech.grupo7.monthlyexpensereport.domain.transacition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {
}
