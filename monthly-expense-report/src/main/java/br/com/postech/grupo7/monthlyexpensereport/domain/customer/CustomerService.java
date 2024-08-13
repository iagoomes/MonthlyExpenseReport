package br.com.postech.grupo7.monthlyexpensereport.domain.customer;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.postech.grupo7.monthlyexpensereport.controller.customer.CustomerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.time.LocalDateTime;

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

    public String createCustomer(CustomerRequest request) {
        var user = customerRepository.findByUserName(request.getUserName());
        if (user != null) {
            return "Usuário já existe";
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, request.getPassword().toCharArray());
        request.setPassword(passwordHashed);

        Customer customer = convertToCustomer(request);
        customerRepository.save(customer);

        return "Customer created successfully";
    }

    public String login(String userName, String password) {
        var user = customerRepository.findByUserName(userName);
        if (user == null || !BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified) {
            return null;
        }

        return "Logado com sucesso";
    }

    private Customer convertToCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setUserName(request.getUserName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        customer.setIncome(request.getIncome());
        customer.setFavoriteBank(request.getFavoriteBank());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        return customer;
    }
}
