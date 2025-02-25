package org.ciopecalina.invoicingapp.repositories;

import org.ciopecalina.invoicingapp.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    List<Invoice> findAllByUserId(Integer userId);

    Invoice findInvoiceById(Integer invoiceId);

    boolean existsByIdAndUserId(Integer invoiceId, Integer userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Invoice i WHERE i.id = :invoiceId AND i.user.id = :userId")
    void deleteByIdAndUserId(@Param("invoiceId") Integer invoiceId, @Param("userId") Integer userId);

}
