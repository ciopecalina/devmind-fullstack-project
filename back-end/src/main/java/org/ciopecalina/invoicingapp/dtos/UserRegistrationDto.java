package org.ciopecalina.invoicingapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 15)
    private String password;

    @JsonProperty("fCode")
    @NotBlank
    @Size(min = 2, max = 10)
    private String fCode;

    @NotBlank
    @Size(min = 8, max = 14)
    private String regNo;

    @NotBlank
    @Size(min = 24, max = 24)
    private String iban;

    @NotBlank
    private String bank;
}
