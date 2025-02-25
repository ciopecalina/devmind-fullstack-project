package org.ciopecalina.invoicingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ciopecalina.invoicingapp.models.InvoiceProduct;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Integer userId;
    private String series;
    private String number;
    private String clientName;
    private LocalDate date;
    private Double totalWithVat;
    private Double totalNoVat;
    private Double vat;

    private Set<InvoiceProductDto> products;
}
