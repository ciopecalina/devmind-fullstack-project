package org.ciopecalina.invoicingapp.repositories;

import org.ciopecalina.invoicingapp.models.StockProduct;
import org.ciopecalina.invoicingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockProductRepository extends JpaRepository<StockProduct, Integer> {
//    void deleteByUser(User user);
}
