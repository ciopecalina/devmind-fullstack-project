package org.ciopecalina.invoicingapp.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.dtos.InvoiceDto;
import org.ciopecalina.invoicingapp.models.Invoice;
import org.ciopecalina.invoicingapp.services.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody InvoiceDto invoice) {
        Invoice newInvoice = invoiceService.saveInvoice(invoice);
        return ResponseEntity.ok(newInvoice);
    }

    @DeleteMapping("/delete/{invoiceId}/{userId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Integer invoiceId, @PathVariable Integer userId) {
        boolean isDeleted = invoiceService.deleteInvoiceByIdAndUserId(invoiceId, userId);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
