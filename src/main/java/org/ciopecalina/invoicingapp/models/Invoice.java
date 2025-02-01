package org.ciopecalina.invoicingapp.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString( exclude = {"user"})
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "series")
    private String series;

    @Column(name = "no")
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "client_name")
    private Integer clientName;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total_with_vat")
    private Double totalWithVat;

    @Column(name = "total_no_vat")
    private Double totalNoVat;

    @Column(name = "vat")
    private Double vat;

    @OneToMany(mappedBy = "invoice")
    @JsonBackReference
    private Set<InvoiceProduct> invoiceProducts;

}
