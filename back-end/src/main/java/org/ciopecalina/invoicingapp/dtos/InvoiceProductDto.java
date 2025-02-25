package org.ciopecalina.invoicingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProductDto {
    private String name;
    private String unitOfMeasurement;
    private Double quantity;
    private Double unitPrice;
    private Double totalWithVat;
    private Double totalNoVat;
    private Double vat;
}
