package org.ciopecalina.invoicingapp.assemblers;

import lombok.NonNull;
import org.ciopecalina.invoicingapp.dtos.StockProductDto;
import org.ciopecalina.invoicingapp.models.StockProduct;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockProductAssembler implements RepresentationModelAssembler<StockProduct, StockProductDto> {
    @Override
    public @NonNull StockProductDto toModel(@NonNull StockProduct entity) {
        return new StockProductDto(
                entity.getUser().getId(),
                entity.getName(),
                entity.getUom(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getTotalWithVat(),
                entity.getTotalNoVat(),
                entity.getVat()
        );
    }

    public List<StockProductDto> toModelList(List<StockProduct> entities) {
        return entities.stream().map(this::toModel).toList();
    }
}