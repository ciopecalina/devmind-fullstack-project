package org.ciopecalina.invoicingapp.controllers;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.dtos.StockProductDto;
import org.ciopecalina.invoicingapp.models.StockProduct;
import org.ciopecalina.invoicingapp.services.StockProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockProductController {
    private final StockProductService stockProductService;

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<StockProduct>> getAllStockProductsByUser(@PathVariable Integer userId) {
        List<StockProduct> products = stockProductService.getStockProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/name/{name}/user/{userId}")
    public ResponseEntity<StockProduct> getStockProductByNameAndUser(
            @PathVariable String name,
            @PathVariable Integer userId
    ) {
        return stockProductService.getStockProductByNameAndUserId(name, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{productId}/{userId}")
    public ResponseEntity<Void> deleteStockProduct(@PathVariable Integer productId, @PathVariable Integer userId) {
        boolean isDeleted = stockProductService.deleteStockProductByIdAndUserId(productId, userId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/add/{userId}")
    public StockProduct addStockProduct(@PathVariable Integer userId, @RequestBody StockProductDto stockProductDto) {
        return stockProductService.addStockProduct(stockProductDto, userId);
    }
}
