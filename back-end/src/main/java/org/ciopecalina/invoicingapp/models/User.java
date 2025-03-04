package org.ciopecalina.invoicingapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@Entity
@ToString(exclude = {"stockProducts", "invoices"})
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

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StockProduct> stockProducts;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Invoice> invoices;

}
