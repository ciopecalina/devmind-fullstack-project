package org.ciopecalina.invoicingapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProductDto {

    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String unitOfMeasurement;

    @NotNull
    @Positive
    private Double quantity;

    @NotNull
    @Positive
    private Double unitPrice;

    @NotNull
    @Positive
    private Double totalWithVat;

    @NotNull
    @Positive
    private Double totalNoVat;

    @NotNull
    @Positive
    private Double vat;
}
