package org.ciopecalina.invoicingapp.services;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.dtos.StockProductDto;
import org.ciopecalina.invoicingapp.models.StockProduct;
import org.ciopecalina.invoicingapp.repositories.StockProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockProductService {
    private final StockProductRepository stockProductRepository;

    public List<StockProduct> getStockProductsByUserId(Integer userId) {
        return stockProductRepository.findAllByUserId(userId);
    }

    public Optional<StockProduct> getStockProductByIdAndUserId(Integer productId, Integer userId) {
        return stockProductRepository.findByIdAndUserId(productId, userId);
    }

    public Optional<StockProduct> getStockProductByNameAndUserId(String name, Integer userId) {
        return stockProductRepository.findByNameIsContainingIgnoreCaseAndUserId(name, userId);
    }

    @Transactional
    public StockProduct createStockProduct(StockProductDto stockProductDto) {
        StockProduct newProduct = new StockProduct();
        newProduct.setName(stockProductDto.getName());
        newProduct.setUom(stockProductDto.getUom());
        newProduct.setQuantity(stockProductDto.getQuantity());
        newProduct.setTotalWithVat(stockProductDto.getTotalWithVat());
        newProduct.setTotalNoVat(stockProductDto.getTotalNoVat());
        newProduct.setVat(stockProductDto.getVat());

        return stockProductRepository.save(newProduct);
    }

    @Transactional
    public boolean deleteStockProductByNameAndUserId(String name, Integer userId) {
        Optional<StockProduct> optionalProduct = stockProductRepository.findByNameIsContainingIgnoreCaseAndUserId(name, userId);
        if (optionalProduct.isPresent()) {
            StockProduct productToDelete = optionalProduct.get();

            stockProductRepository.delete(productToDelete);

            return true;
        }
        return false;
    }

}
