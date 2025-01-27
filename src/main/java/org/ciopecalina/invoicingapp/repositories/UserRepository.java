package org.ciopecalina.invoicingapp.repositories;

import org.ciopecalina.invoicingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
//
//    Optional<Student> findByNameAndFirstName(String name, String firstName);
//
//    Optional<Student> findByNameContaining(String fistName);
//
//    List<Student> findAllByNameContaining(String name);
//
//    List<Student> findAllByAdresa_CityOrderByIdDesc(String city);
}