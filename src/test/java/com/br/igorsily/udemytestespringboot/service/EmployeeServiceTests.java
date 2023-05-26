package com.br.igorsily.udemytestespringboot.service;

import com.br.igorsily.udemytestespringboot.dto.v1.EmployeeDTO;
import com.br.igorsily.udemytestespringboot.exception.BadRequestException;
import com.br.igorsily.udemytestespringboot.exception.EntityNotFoundException;
import com.br.igorsily.udemytestespringboot.mapper.EmployeeMapper;
import com.br.igorsily.udemytestespringboot.model.Employee;
import com.br.igorsily.udemytestespringboot.repository.EmployeeRepository;
import com.br.igorsily.udemytestespringboot.service.impl.EmployeeServiceImpl;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTests {

    private final Faker faker = new Faker(new Locale("pt", "BR"));

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    public void setUp() {

        this.employeeDTO = createEmployeeDTO();

    }

    @Test
    @DisplayName("Given EmployeeDTO when Save Employee then Return Saved EmployeeDTO")
    void givenEmployeeDTO_whenSaveEmployee_thenReturnSavedEmployeeDTO() {

        //Given - precondition or setup
        Employee employee = EmployeeMapper.INSTANCE.toEntity(employeeDTO);
        given(employeeRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(employee));
        given(employeeRepository.save(Mockito.any(Employee.class))).willReturn(employee);

        //When - action or the behaviour that we are going test
        EmployeeDTO savedEmployeeDTO = employeeService.saveEmployee(employeeDTO);

        //Then - assertion or verification
        assertThat(savedEmployeeDTO).isNotNull();
        assertThat(savedEmployeeDTO.getId()).isNotNull();

    }

    @Test
    @DisplayName("Given EmployeeDTO when Already exists email then Return BadRequestException")
    void givenEmployeeDTO_whenAlreadyExistsEmail_thenReturnBadRequestException() {

        //Given - precondition or setup
        Employee employee = EmployeeMapper.INSTANCE.toEntity(employeeDTO);

        given(employeeRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(employee));

        //When - action or the behaviour that we are going test
        assertThrows(BadRequestException.class, () -> employeeService.saveEmployee(employeeDTO));

        //Then - assertion or verification
        verify(employeeRepository, Mockito.never()).save(Mockito.any(Employee.class));

    }

    @Test
    @DisplayName("Given Employee List when Find All Employees then Return EmployeeDTO List")
    void givenEmployeeList_whenFindAllEmployees_thenReturnEmployeeDTOList() {

        //Given - precondition or setup

        List<Employee> employees = getEmployees();
        given(employeeRepository.findAll()).willReturn(employees);

        //When - action or the behaviour that we are going test
        List<EmployeeDTO> employeeList = employeeService.findAllEmployees();

        //Then - assertion or verification
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.size()).isEqualTo(employees.size());

    }

    @Test
    @DisplayName("Given Employee List when Find All Employees then Return Empty List")
    void givenEmployeeList_whenFindAllEmployees_thenReturnEmptyList() {

        //Given - precondition or setup

        given(employeeRepository.findAll()).willReturn(List.of());

        //When - action or the behaviour that we are going test
        List<EmployeeDTO> employeeList = employeeService.findAllEmployees();

        //Then - assertion or verification
        assertThat(employeeList).isEmpty();

    }

    @Test
    @DisplayName("Given ID when get employee by id then Return EmployeeDTO")
    void givenId_whenGetEmployeeById_thenReturnEmployeeDTO() {

        //Given - precondition or setup
        Employee employee = EmployeeMapper.INSTANCE.toEntity(employeeDTO);
        given(employeeRepository.findById(Mockito.any(UUID.class))).willReturn(Optional.of(employee));

        //When - action or the behaviour that we are going test
        EmployeeDTO employeeDTO = employeeService.findEmployeeById(employee.getId());

        //Then - assertion or verification
        assertThat(employeeDTO).isNotNull();

    }

    @Test
    @DisplayName("Given ID when ID not exists  then Return BadRequestException")
    void givenId_whenIdNotExists_thenReturnBadRequestException() {

        //Given - precondition or setup
        given(employeeRepository.findById(Mockito.any(UUID.class))).willReturn(Optional.empty());

        //When - action or the behaviour that we are going test
        assertThrows(BadRequestException.class, () -> employeeService.findEmployeeById(UUID.fromString(faker.internet().uuid())));

        //Then - assertion or verification
        //verify(employeeRepository, Mockito.never()).findById(Mockito.any(UUID.class));

    }

    @Test
    @DisplayName("Given EmployeeDTO when Update Employee  then Return Update EmployeeDTO")
    void givenEmployeeDTO_whenUpdateEmployee_thenReturnUpdateEmployeeDTO() {

        //Given - precondition or setup
        Employee employee = EmployeeMapper.INSTANCE.toEntity(employeeDTO);
        given(employeeRepository.findById(Mockito.any(UUID.class))).willReturn(Optional.of(employee));
        employeeDTO.setFirstName(faker.name().firstName());
        employeeDTO.setLastName(faker.name().lastName());

        //When - action or the behaviour that we are going test
        EmployeeDTO employeeDTOUpdate = employeeService.updateEmployee(employeeDTO);

        //Then - assertion or verification
        assertThat(employeeDTOUpdate).isNotNull();
        assertThat(employeeDTOUpdate.getFirstName()).isEqualTo(employeeDTO.getFirstName());
        assertThat(employeeDTOUpdate.getLastName()).isEqualTo(employeeDTO.getLastName());

    }

    @Test
    @DisplayName("Given EmployeeDTO With ID null when Update Employee  then Return BadRequestException")
    void givenEmployeeDTOWithIdNull_whenUpdateEmployee_thenReturnBadRequestException() {

        //Given - precondition or setup
        employeeDTO.setId(null);

        //When - action or the behaviour that we are going test
        assertThrows(BadRequestException.class, () -> employeeService.updateEmployee(employeeDTO));

        //Then - assertion or verification
        verify(employeeRepository, Mockito.never()).save(Mockito.any(Employee.class));

    }

    @Test
    @DisplayName("Given EmployeeId when Delete Employee  then Return Nothing")
    void givenEmployeeIdl_whenDeleteEmployee_thenReturnNothing() {

        //Given - precondition or setup
        UUID id = UUID.fromString(faker.internet().uuid());
        willDoNothing().given(employeeRepository).deleteById(Mockito.any(UUID.class));
        given(employeeRepository.findById(Mockito.any(UUID.class))).willReturn(Optional.of(Employee.builder().build()));

        //When - action or the behaviour that we are going test
        employeeService.deleteEmployee(id);

        //Then - assertion or verification
        verify(employeeRepository, times(1)).deleteById(id);

    }


    private EmployeeDTO createEmployeeDTO() {
        return EmployeeDTO.builder()
                .id(UUID.fromString(faker.internet().uuid()))
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .build();
    }

    private List<Employee> getEmployees() {

        return List.of(
                Employee.builder()
                        .id(UUID.fromString(faker.internet().uuid()))
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .email(faker.internet().emailAddress())
                        .build(),
                Employee.builder()
                        .id(UUID.fromString(faker.internet().uuid()))
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .email(faker.internet().emailAddress())
                        .build(),
                Employee.builder()
                        .id(UUID.fromString(faker.internet().uuid()))
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .email(faker.internet().emailAddress())
                        .build()
        );
    }
}
