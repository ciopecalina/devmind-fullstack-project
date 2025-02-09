package org.ciopecalina.invoicingapp.controllers;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.assemblers.StockProductAssembler;
import org.ciopecalina.invoicingapp.dtos.StockProductDto;
import org.ciopecalina.invoicingapp.dtos.UserRegistrationDto;
import org.ciopecalina.invoicingapp.models.StockProduct;
import org.ciopecalina.invoicingapp.models.User;
import org.ciopecalina.invoicingapp.services.StockProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock-products")
public class StockProductController {
    private final StockProductService stockProductService;
    private final StockProductAssembler stockProductAssembler;

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<StockProduct>> getAllStockProductsByUser(@PathVariable Integer userId) {
        List<StockProduct> products = stockProductService.getStockProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{productId}/user/{userId}")
    public ResponseEntity<StockProduct> getStockProductByIdAndUser(
            @PathVariable Integer productId, @PathVariable Integer userId
    ) {
        return stockProductService.getStockProductByIdAndUserId(productId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //http://localhost:8080/stock-products/name/Laptop%20ASUS%20VivoBook/user/2
    @GetMapping("/name/{name}/user/{userId}")
    public ResponseEntity<StockProduct> getStockProductByNameAndUser(
            @PathVariable String name,
            @PathVariable Integer userId
    ) {
        return stockProductService.getStockProductByNameAndUserId(name, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{name}/{userId}")
    public ResponseEntity<Void> deleteStockProductByNameAndUserId(
            @PathVariable String name,
            @PathVariable Integer userId
    ) {
        boolean isDeleted = stockProductService.deleteStockProductByNameAndUserId(name, userId);

        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/add-stock-product/")
    public ResponseEntity<StockProductDto> createStockProduct(@RequestBody StockProductDto stockProductDto) {
        StockProduct product = stockProductService.createStockProduct(stockProductDto);

        StockProductDto productDto = stockProductAssembler.toModel(product);

        return ResponseEntity.ok(productDto);
    }
}
