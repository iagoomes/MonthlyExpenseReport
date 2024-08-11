package br.com.postech.grupo7.monthlyexpensereport.controller;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import br.com.postech.grupo7.monthlyexpensereport.domain.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
