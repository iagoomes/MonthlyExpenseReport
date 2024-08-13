package br.com.postech.grupo7.monthlyexpensereport.controller.customer;

import lombok.Data;

@Data
public class LoginRequest {
    private String userName;
    private String password;
}
