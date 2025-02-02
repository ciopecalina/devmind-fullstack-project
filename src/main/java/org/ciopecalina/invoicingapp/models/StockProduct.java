package org.ciopecalina.invoicingapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"user", "invoiceProducts"})
@Entity
@Table(name = "stock_products")
public class StockProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "uom")
    private String uom;

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

    @JsonIgnore
    @OneToMany(mappedBy = "stockProduct")
    private Set<InvoiceProduct> invoiceProducts;

    public void calculateTotals() {
        if (unitPrice != null && quantity != null) {
            totalNoVat = unitPrice * quantity;
            vat = totalNoVat * 0.19;
            totalWithVat = totalNoVat + vat;
        }
    }
}
