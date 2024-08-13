package br.com.postech.grupo7.monthlyexpensereport.domain.validation_engine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationEngineRepository extends JpaRepository<ValidationEngine, Integer> {
}
