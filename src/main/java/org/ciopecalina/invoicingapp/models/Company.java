package org.ciopecalina.invoicingapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "companies")
@ToString(exclude = "users")

public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "f_code")
    private String fiscalCode;

    @Column(name = "reg_no")
    private String registrationNumber;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bank")
    private String bank;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "company")
    private Set<StockProduct> stockProducts;

    @OneToMany(mappedBy = "company")
    private Set<Invoice> invoices;
}
