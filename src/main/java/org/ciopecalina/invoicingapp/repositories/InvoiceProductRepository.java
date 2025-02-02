package org.ciopecalina.invoicingapp.repositories;

import org.ciopecalina.invoicingapp.models.InvoiceProduct;
import org.ciopecalina.invoicingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Integer> {
//    void deleteByUser(User user);
}
