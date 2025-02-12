package org.ciopecalina.invoicingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private Integer id;
    private String name;
    private String fCode;
    private String regNo;
    private String iban;
    private String bank;
}
