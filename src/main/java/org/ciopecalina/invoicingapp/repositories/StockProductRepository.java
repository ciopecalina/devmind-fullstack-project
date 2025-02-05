package org.ciopecalina.invoicingapp.repositories;

import org.ciopecalina.invoicingapp.models.StockProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockProductRepository extends JpaRepository<StockProduct, Integer> {
    List<StockProduct> findAllByUserId(int userId);

    Optional<StockProduct> findByIdAndUserId(Integer id, Integer userId);

    Optional<StockProduct> findByNameIsContainingIgnoreCaseAndUserId(String name, Integer userId);

}
