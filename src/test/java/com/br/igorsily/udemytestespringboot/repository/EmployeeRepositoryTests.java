package com.br.igorsily.udemytestespringboot.repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeRepositoryTest {

    private  DataSource dataSource;

    private  JdbcTemplate jdbcTemplate;

    private  EntityManager entityManager;

    private  EmployeeRepository employeeRepository;


    @Test
    public void injectedComponentsAreNotNull(){

        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(employeeRepository).isNotNull();
    }
}
