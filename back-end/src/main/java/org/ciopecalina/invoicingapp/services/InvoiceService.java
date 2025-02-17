package org.ciopecalina.invoicingapp.services;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.models.Invoice;
import org.ciopecalina.invoicingapp.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public List<Invoice> getInvoicesByUserId(Integer userId) {
        return invoiceRepository.findAllByUserId(userId);
    }

    public Invoice getInvoiceById(Integer invoiceId) {
        return invoiceRepository.findInvoiceById(invoiceId);
    }
}
