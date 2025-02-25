package org.ciopecalina.invoicingapp.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockProductDto {

    @NotNull
    private Integer userId;

    @NotBlank
    private String name;

    @NotBlank
    private String uom;

    @Positive
    private Double quantity;

    @Positive
    private Double unitPrice;

    @Positive
    private Double totalWithVat;

    @Positive
    private Double totalNoVat;

    @Positive
    private Double vat;
}
