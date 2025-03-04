package org.ciopecalina.invoicingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String email;
    private String name;
    private Boolean isApproved;
    private Boolean isAdmin;
}
