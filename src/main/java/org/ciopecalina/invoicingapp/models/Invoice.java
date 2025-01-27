package org.ciopecalina.invoicingapp.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
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
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "date")
    private Date date;

    @Column(name = "total")
    private Double total;

    @Column(name = "total_vat")
    private Double totalVat;

    @Column(name = "total_no_vat")
    private Double totalNoVat;

    @OneToMany(mappedBy = "invoice")
    private Set<InvoiceProduct> invoiceProducts;

}
