package org.ciopecalina.invoicingapp.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String fCode;
    private String regNo;
    private String iban;
    private String bank;
}
