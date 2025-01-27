package org.ciopecalina.invoicingapp.models;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "is_admin")
    private Boolean isAdmin;
}
