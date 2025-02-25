package org.ciopecalina.invoicingapp.services;

import lombok.RequiredArgsConstructor;
import org.ciopecalina.invoicingapp.dtos.InvoiceDto;
import org.ciopecalina.invoicingapp.dtos.InvoiceProductDto;
import org.ciopecalina.invoicingapp.models.Invoice;
import org.ciopecalina.invoicingapp.models.InvoiceProduct;
import org.ciopecalina.invoicingapp.models.User;
import org.ciopecalina.invoicingapp.repositories.InvoiceRepository;
import org.ciopecalina.invoicingapp.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public List<Invoice> getInvoicesByUserId(Integer userId) {
        return invoiceRepository.findAllByUserId(userId);
    }

    public Invoice getInvoiceById(Integer invoiceId) {
        return invoiceRepository.findInvoiceById(invoiceId);
    }

    @Transactional
    public Invoice saveInvoice(InvoiceDto invoiceDto) {
        User user = userRepository.findById(invoiceDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Invoice invoice = new Invoice();
        invoice.setUser(user);
        invoice.setSeries(invoiceDto.getSeries());
        invoice.setNumber(invoiceDto.getNumber());
        invoice.setClientName(invoiceDto.getClientName());
        invoice.setDate(invoiceDto.getDate());
        invoice.setTotalWithVat(invoiceDto.getTotalWithVat());
        invoice.setTotalNoVat(invoiceDto.getTotalNoVat());
        invoice.setVat(invoiceDto.getVat());

        Set<InvoiceProduct> invoiceProducts = new HashSet<>();

        for (InvoiceProductDto productDto : invoiceDto.getProducts()) {
            InvoiceProduct product = new InvoiceProduct();
            product.setInvoice(invoice);
            product.setName(productDto.getName());
            product.setUnitOfMeasurement(productDto.getUnitOfMeasurement());
            product.setQuantity(productDto.getQuantity());
            product.setUnitPrice(productDto.getUnitPrice());
            product.setTotalNoVat(productDto.getTotalNoVat());
            product.setVat(productDto.getVat());
            product.setTotalWithVat(productDto.getTotalWithVat());

            invoiceProducts.add(product);
        }

        invoice.setInvoiceProducts(invoiceProducts);

        return invoiceRepository.save(invoice);
    }

    @Transactional
    public boolean deleteInvoiceByIdAndUserId(Integer invoiceId, Integer userId) {
        if (invoiceRepository.existsByIdAndUserId(invoiceId, userId)) {
            invoiceRepository.deleteByIdAndUserId(invoiceId, userId);
            return true;
        }
        return false;
    }
}
