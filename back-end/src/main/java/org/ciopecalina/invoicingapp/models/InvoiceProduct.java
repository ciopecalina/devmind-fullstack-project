package org.ciopecalina.invoicingapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@ToString(exclude = {"invoice", "stockProduct"})
@Entity
@Table(name = "invoice_products")
public class InvoiceProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_product_id")
    private StockProduct stockProduct;

    @Column(name = "name")
    private String name;

    @Column(name = "uom")
    private String unitOfMeasurement;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "total_with_vat")
    private Double totalWithVat;

    @Column(name = "total_no_vat")
    private Double totalNoVat;

    @Column(name = "vat")
    private Double vat;
}
