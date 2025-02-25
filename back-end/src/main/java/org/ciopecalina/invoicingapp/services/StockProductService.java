package org.ciopecalina.invoicingapp.services;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.dtos.StockProductDto;
import org.ciopecalina.invoicingapp.models.StockProduct;
import org.ciopecalina.invoicingapp.repositories.StockProductRepository;
import org.ciopecalina.invoicingapp.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockProductService {
    private final StockProductRepository stockProductRepository;
    private final UserRepository userRepository;

    public List<StockProduct> getStockProductsByUserId(Integer userId) {
        return stockProductRepository.findAllByUserId(userId);
    }

    @Transactional
    public boolean deleteStockProductByIdAndUserId(Integer productId, Integer userId) {
        if (stockProductRepository.existsByIdAndUserId(productId, userId)) {
            stockProductRepository.deleteByIdAndUserId(productId, userId);
            return true;
        }
        return false;
    }

    public Optional<StockProduct> getStockProductByNameAndUserId(String name, Integer userId) {
        return stockProductRepository.findByNameIsContainingIgnoreCaseAndUserId(name, userId);
    }

    @Transactional
    public StockProduct addStockProduct(StockProductDto stockProductDto, Integer userId) {
        StockProduct newProduct = new StockProduct();

        newProduct.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found")));

        newProduct.setName(stockProductDto.getName());
        newProduct.setUom(stockProductDto.getUom());
        newProduct.setQuantity(stockProductDto.getQuantity());
        newProduct.setUnitPrice(stockProductDto.getUnitPrice());
        newProduct.setTotalNoVat(stockProductDto.getTotalNoVat());
        newProduct.setVat(stockProductDto.getVat());
        newProduct.setTotalWithVat(stockProductDto.getTotalWithVat());

        return stockProductRepository.save(newProduct);
    }

}
