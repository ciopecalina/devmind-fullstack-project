package org.ciopecalina.invoicingapp.models;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "invoice_products")
public class InvoiceProduct {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@ManyToOne
@JoinColumn(name = "invoice_id")
private Invoice invoice;

@ManyToOne
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

@Column(name = "total_vat")
private Double totalVat;

@Column(name = "total_no_vat")
private Double totalNoVat;

@Column(name = "vat")
private Double vat;
}
