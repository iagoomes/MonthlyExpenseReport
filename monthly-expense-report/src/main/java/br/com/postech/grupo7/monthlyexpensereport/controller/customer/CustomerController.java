package br.com.postech.grupo7.monthlyexpensereport.controller.customer;

import br.com.postech.grupo7.monthlyexpensereport.domain.customer.Customer;
import br.com.postech.grupo7.monthlyexpensereport.domain.customer.CustomerRepository;
import br.com.postech.grupo7.monthlyexpensereport.domain.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CustomerRequest request) {
        try {
            String result = customerService.createCustomer(request);
            if ("Usuário já existe".equals(result)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar o customer: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = customerService.login(loginRequest.getUserName(), loginRequest.getPassword());
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }
        return ResponseEntity.ok(token);
    }
}
