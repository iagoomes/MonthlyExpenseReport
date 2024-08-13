package br.com.postech.grupo7.monthlyexpensereport.domain.file.server;

import java.time.LocalDateTime;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "file_server")
public class FileServer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String name;

    @Column(name = "data", columnDefinition = "BLOB")
    private String data;

    private LocalDateTime createdAt;

    public FileServer() {
        this.name = null;
        this.data = null;
        this.createdAt = null;
    }

    public FileServer(String name, String data, Customer customer) {
        this.name = name;
        this.data = data;
        this.customer = customer;
        createdAt = LocalDateTime.now();
    }

}