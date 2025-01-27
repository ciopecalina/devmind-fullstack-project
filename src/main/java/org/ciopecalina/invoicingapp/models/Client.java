package org.ciopecalina.invoicingapp.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "f_code")
    private String fiscalCode;

    @Column(name = "reg_no")
    private String registerNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Invoice> invoices;

}
