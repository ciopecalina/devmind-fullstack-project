package org.ciopecalina.invoicingapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.ciopecalina.invoicingapp.models.*;
import org.ciopecalina.invoicingapp.models.User;
import org.ciopecalina.invoicingapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvoicingApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(InvoicingApplication.class, args);
    }

//    @Autowired
//    private UserRepository studentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception{
       // System.out.println(studentRepository.findByEmail("alina@gmail.com"));

//        User user = (User) entityManager
//                .createNativeQuery("select id, email, password, approved, is_admin from users where email = :email", User.class)
//                .setParameter("email", "alina@gmail.com")
//                .getSingleResult();

        Client client = entityManager.find(Client.class, 1L);
        System.out.println(client);

    }
}
