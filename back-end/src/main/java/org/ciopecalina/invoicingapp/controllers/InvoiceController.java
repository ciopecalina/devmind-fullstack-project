package org.ciopecalina.invoicingapp.controllers;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.assemblers.StockProductAssembler;
import org.ciopecalina.invoicingapp.models.Invoice;
import org.ciopecalina.invoicingapp.models.StockProduct;
import org.ciopecalina.invoicingapp.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<Invoice>> getAllInvoicesByUserId(@PathVariable Integer userId) {
        List<Invoice> invoices = invoiceService.getInvoicesByUserId(userId);
        return ResponseEntity.ok(invoices);
    }
}
