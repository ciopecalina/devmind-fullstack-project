package org.ciopecalina.invoicingapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockProductDto extends RepresentationModel<StockProductDto> {
    private Integer userId;
    private String name;
    private String uom;
    private Double quantity;
    private Double unitPrice;
    private Double totalWithVat;
    private Double totalNoVat;
    private Double vat;

}
