package org.ciopecalina.invoicingapp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "f_code")
    private String fCode;

    @Column(name = "reg_no")
    private String regNo;

    @Column(name = "iban")
    private String iban;

    @Column(name = "bank")
    private String bank;

//    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private Set<StockProduct> stockProducts;


//    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Invoice> invoices;
}
