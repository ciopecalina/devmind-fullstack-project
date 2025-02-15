package org.ciopecalina.invoicingapp.repositories;

import org.ciopecalina.invoicingapp.dtos.UserDetailsDto;
import org.ciopecalina.invoicingapp.dtos.UserSecurityDto;
import org.ciopecalina.invoicingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);

    List<User> findAll();

    Optional<UserSecurityDto> findByEmailAndPassword(String email, String password);

    List<User> findAllByIsAdminFalseOrderByIdDesc();

    Optional<User> findByNameIsContainingIgnoreCaseAndEmail(String name, String email);

    @Override
    <S extends User> S save(S entity);

    Optional<User> findByEmail(String email);
    User findUserByName(String name);
}
