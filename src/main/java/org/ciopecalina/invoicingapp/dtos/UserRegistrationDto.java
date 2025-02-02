package org.ciopecalina.invoicingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationDto extends RepresentationModel<UserRegistrationDto> {
    private Boolean isAdmin;
    private Boolean isApproved;
    private String email;
    private String password;
    private String name;
    private String fCode;
    private String regNo;
    private String iban;
    private String bank;

    public UserRegistrationDto(String email, String password, String name, String fCode, String regNo, String iban, String bank) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.fCode = fCode;
        this.regNo = regNo;
        this.iban = iban;
        this.bank = bank;
        this.isAdmin = false;
        this.isApproved = false;
    }
}
