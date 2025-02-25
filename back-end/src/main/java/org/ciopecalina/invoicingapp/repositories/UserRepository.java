package org.ciopecalina.invoicingapp.repositories;

import org.ciopecalina.invoicingapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);

    List<User> findAllByIsAdminFalseOrderByIdDesc();

    Optional<User> findByEmail(String email);

    User findUserByName(String name);

    @Query("SELECT c.name FROM User c WHERE c.name <> :name")
    List<String> findAllClientNamesExcluding(@Param("name") String name);
}
