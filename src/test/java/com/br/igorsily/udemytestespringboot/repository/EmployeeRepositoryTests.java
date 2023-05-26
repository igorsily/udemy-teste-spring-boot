package com.br.igorsily.udemytestespringboot.repository;

import com.br.igorsily.udemytestespringboot.model.Employee;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeRepositoryTests {

    private final EmployeeRepository employeeRepository;

    private final Faker faker = new Faker(new Locale("pt", "BR"));

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = createEmployee();
    }

    @Test
    @DisplayName("Given Employee when Save then Return Saved Employee")
    public void givenEmployee_whenSave_thenReturnSavedEmployee() {

        //Given - precondition or setup
        //Employee employee = createEmployee();

        //When - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(this.employee);

        //Then - assertion or verification
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isNotNull();

    }

    @Test
    @DisplayName("Given EmployeeList when FindAll then Return ListEmployee")
    public void givenEmployeeList_whenFindAll_thenReturnListEmployee() {

        //Given - precondition or setup

        List<Employee> employeeListAux = new ArrayList<>();
        for (int i = 0; i < getRandomNumber(); i++) {
            employeeListAux.add(createEmployee());
        }

        employeeRepository.saveAll(employeeListAux);

        //When - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        //Then - assertion or verification
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.size()).isEqualTo(employeeListAux.size());

    }

    @Test
    @DisplayName("Given Employee when FindById then Return Employee")
    public void givenEmployee_whenFindById_thenReturnEmployeeById() {

        //Given - precondition or setup

        //Employee employeeAux = createEmployee();

        employeeRepository.save(this.employee);

        //When - action or the behaviour that we are going test
        Optional<Employee> employee = employeeRepository.findById(this.employee.getId());

        //Then - assertion or verification
        assertThat(employee).isNotNull();
        assertThat(employee.isPresent()).isTrue();
        assertThat(employee.get().getId()).isEqualTo(this.employee.getId());

    }

    @Test
    @DisplayName("Given Employee when FindByEmail then Return Employee")
    public void givenEmployee_whenFindByEmail_thenReturnEmployee() {

        //Given - precondition or setup

//        Employee employeeAux = createEmployee();

        employeeRepository.save(this.employee);

        //When - action or the behaviour that we are going test
        Optional<Employee> employee = employeeRepository.findByEmail(this.employee.getEmail());

        //Then - assertion or verification
        assertThat(employee).isNotNull();
        assertThat(employee.isPresent()).isTrue();
        assertThat(employee.get().getFirstName()).isEqualTo(this.employee.getFirstName());

    }

    @Test
    @DisplayName("Given Employee when FindByEmail then Return Employee")
    public void givenEmployee_whenUpdate_thenReturnUpdateEmployee() {

        //Given - precondition or setup
//        Employee employee = createEmployee();
        employeeRepository.save(this.employee);
        String nameUpddate = faker.name().firstName();

        //When - action or the behaviour that we are going test
        Employee employeeSaved = employeeRepository.findById(this.employee.getId()).get();
        employeeSaved.setFirstName(nameUpddate);
        Employee updateEmployee = employeeRepository.save(employeeSaved);

        //Then - assertion or verification
        assertThat(updateEmployee).isNotNull();
        assertThat(updateEmployee.getFirstName()).isEqualTo(nameUpddate);

    }

    @Test
    @DisplayName("Given Employee when Delete then Return Remove Employee")
    public void givenEmployee_whenDelete_thenRemoveEmployee() {

        //Given - precondition or setup
        //Employee employee = createEmployee();
        employeeRepository.save(this.employee);


        //When - action or the behaviour that we are going test
        employeeRepository.delete(this.employee);

        Optional<Employee> employeeSaved = employeeRepository.findById(this.employee.getId());

        //Then - assertion or verification
        assertThat(employeeSaved.isEmpty()).isTrue();
    }

    private Employee createEmployee() {
        return Employee.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();
    }

    private int getRandomNumber() {
        return (int) ((Math.random() * (10 - 1)) + 1);
    }


}
