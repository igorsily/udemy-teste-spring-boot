package com.br.igorsily.udemytestespringboot.repository;

import com.br.igorsily.udemytestespringboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Optional<Employee> findByEmail(@Param("email") String email);
}
