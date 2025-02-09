package org.ciopecalina.invoicingapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.ciopecalina.invoicingapp.models.User;
import org.ciopecalina.invoicingapp.repositories.UserRepository;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class InvoicingApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(InvoicingApplication.class, args);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

    }
}
