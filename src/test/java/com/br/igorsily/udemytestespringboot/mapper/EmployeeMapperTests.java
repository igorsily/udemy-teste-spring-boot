package com.br.igorsily.udemytestespringboot.mapper;

import com.br.igorsily.udemytestespringboot.dto.v1.EmployeeDTO;
import com.br.igorsily.udemytestespringboot.model.Employee;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeMapperTests {

    private final Faker faker = new Faker(new Locale("pt", "BR"));


    @Test
    @DisplayName("Given EmployeeDTO when Map EmployDTO To Employee then Return  Employee")
    public void givenEmployeeDTO_whenMapEmployeeDTOToEmployee_thenReturnEmployee() {

        //Given - precondition or setup
        EmployeeDTO employeeDTO = createEmployeeDTO();

        //When - action or the behaviour that we are going test

        GenericMapper employee = EmployeeMapper.INSTANCE.toEntity(employeeDTO);

        //Then - assertion or verification

        assertThat(employee).isNotNull();
        assertThat(employee.getFirstName()).isEqualTo(employeeDTO.getFirstName());
        assertThat(employee.getLastName()).isEqualTo(employeeDTO.getLastName());
        assertThat(employee.getEmail()).isEqualTo(employeeDTO.getEmail());
    }

    @Test
    @DisplayName("Given Employee when Map Employ To EmployeeDTO then Return  EmployeeDTO")
    public void givenEmployee_whenMapEmployeeToEmployeeDTO_thenReturnEmployeeDTO() {

        //Given - precondition or setup

        Employee employee = createEmployee();

        //When - action or the behaviour that we are going test

        EmployeeDTO employeeDTO = EmployeeMapper.INSTANCE.toDTO(employee);

        //Then - assertion or verification

        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getFirstName()).isEqualTo(employee.getFirstName());
        assertThat(employeeDTO.getLastName()).isEqualTo(employee.getLastName());
        assertThat(employeeDTO.getEmail()).isEqualTo(employee.getEmail());
    }

    @Test
    @DisplayName("Given EmployeeDTO List when Map EmployDTO List To Employee List then Return Employee List")
    public void givenEmployeeDTOList_whenMapEmployeeDTOListToEmployeeList_thenReturnEmployeeList(){

        //Given - precondition or setup

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (int i = 0; i < getRandomNumber(); i++) {
            employeeDTOList.add(createEmployeeDTO());
        }

        //When - action or the behaviour that we are going test

        List<Employee> employeeList = EmployeeMapper.INSTANCE.toEntityList(employeeDTOList);

        //Then - assertion or verification

        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.size()).isEqualTo(employeeDTOList.size());

    }

    @Test
    @DisplayName("Given Employee List when Map Employ List To EmployeeDTO List then Return  EmployeeDTO List")
    public void givenEmployeeList_whenMapEmployeeListToEmployeeDTOList_thenReturnEmployeeDTOList(){

        //Given - precondition or setup

        List<Employee> employeeList = new ArrayList<>();

        for (int i = 0; i < getRandomNumber(); i++) {
            employeeList.add(createEmployee());
        }

        //When - action or the behaviour that we are going test

        List<EmployeeDTO> employeeDTOList = EmployeeMapper.INSTANCE.toDTOList(employeeList);

        //Then - assertion or verification

        assertThat(employeeDTOList).isNotNull();
        assertThat(employeeDTOList).isNotEmpty();
        assertThat(employeeDTOList.size()).isEqualTo(employeeList.size());

    }


    private Employee createEmployee() {
        return Employee.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();
    }

    private EmployeeDTO createEmployeeDTO() {
        return EmployeeDTO.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();
    }

    private int getRandomNumber() {
        return (int) ((Math.random() * (10 - 1)) + 1);
    }
}
