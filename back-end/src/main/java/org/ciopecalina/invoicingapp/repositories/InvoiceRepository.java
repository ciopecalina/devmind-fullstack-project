package org.ciopecalina.invoicingapp.repositories;

import org.ciopecalina.invoicingapp.models.Invoice;
import org.ciopecalina.invoicingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
//    void deleteByUser(User user);
}
