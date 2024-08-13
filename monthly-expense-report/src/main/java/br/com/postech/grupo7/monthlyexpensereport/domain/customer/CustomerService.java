package br.com.postech.grupo7.monthlyexpensereport.domain.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
