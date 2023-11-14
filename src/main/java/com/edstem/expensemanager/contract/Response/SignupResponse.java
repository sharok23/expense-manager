package com.edstem.expensemanager.contract.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private Long id;
    private String name;
    private String email;
    private String hashedPassword;
}
