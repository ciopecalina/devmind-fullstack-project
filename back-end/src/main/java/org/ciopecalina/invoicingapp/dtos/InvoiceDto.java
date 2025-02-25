package org.ciopecalina.invoicingapp.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    @NotNull
    private Integer userId;

    @NotBlank
    @Size(max = 10)
    private String series;

    @NotBlank
    @Size(max = 10)
    private String number;

    @NotBlank
    @Size(max = 100)
    private String clientName;

    @NotNull
    private LocalDate date;

    @NotNull
    @Positive
    private Double totalWithVat;

    @NotNull
    @Positive
    private Double totalNoVat;

    @NotNull
    @Positive
    private Double vat;

    @NotEmpty
    private Set<@Valid InvoiceProductDto> products;
}
