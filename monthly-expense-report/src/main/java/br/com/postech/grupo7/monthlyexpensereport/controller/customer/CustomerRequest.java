package br.com.postech.grupo7.monthlyexpensereport.controller.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be at most 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be at most 50 characters")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must be at most 50 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(max = 100, message = "Password must be at most 100 characters")
    private String password;

    @NotBlank(message = "Income is required")
    @Size(max = 100, message = "Income must be at most 100 characters")
    private String income;

    @NotBlank(message = "Favorite bank is required")
    @Size(max = 100, message = "Favorite bank must be at most 100 characters")
    private String favoriteBank;
}


