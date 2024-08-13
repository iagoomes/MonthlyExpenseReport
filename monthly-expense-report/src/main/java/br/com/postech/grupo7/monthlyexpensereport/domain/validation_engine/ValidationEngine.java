package br.com.postech.grupo7.monthlyexpensereport.domain.validation_engine;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@Entity
@Table(name = "validation_engine")
public class ValidationEngine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private byte[] data;
    private LocalDateTime createdAt;

    public ValidationEngine() {
        this.name = null;
        this.data = null;
        this.createdAt = null;
    }

    public ValidationEngine(String name, byte[] data, LocalDateTime createdAt) {
        this.name = name;
        this.data = data;
        this.createdAt = createdAt;
    }

}