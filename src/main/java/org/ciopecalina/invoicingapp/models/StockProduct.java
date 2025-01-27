package org.ciopecalina.invoicingapp.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "stock_products")
public class StockProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "name")
    private String name;

    @Column(name = "uom")
    private String unitOfMeasurement;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_vat")
    private Double totalVat;

    @Column(name = "total_no_vat")
    private Double totalNoVat;

    @Column(name = "vat")
    private Double vat;

    @OneToMany(mappedBy = "stockProduct")
    private Set<InvoiceProduct> invoiceProducts;

    @OneToMany(mappedBy = "stockProduct")
    private Set<StockAdjustment> stockAdjustments;
}
