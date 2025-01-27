package org.ciopecalina.invoicingapp.models;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "stock_adjustments")
public class StockAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "stock_product_id")
    private StockProduct stockProduct;

    @Column(name = "adjustment_type")
    private String adjustmentType; //"addition" or "removal"

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "price")
    private Double price;
}
