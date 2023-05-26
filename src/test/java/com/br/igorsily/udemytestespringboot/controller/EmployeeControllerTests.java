package com.br.igorsily.udemytestespringboot.controller;

import com.br.igorsily.udemytestespringboot.dto.v1.EmployeeDTO;
import com.br.igorsily.udemytestespringboot.mapper.EmployeeMapper;
import com.br.igorsily.udemytestespringboot.model.Employee;
import com.br.igorsily.udemytestespringboot.service.EmployeeService;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeControllerTests {

    private final Faker faker = new Faker(new Locale("pt", "BR"));
    private final MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private EmployeeDTO employeeDTO;

    @BeforeEach
    public void setUp() {
        employeeDTO = createEmployeeDTO();
    }


    @Test
    @DisplayName("Given EmployeeDTO  When Create Employee  Then Return EmployeeDTO ")
    public void givenEmployeeDTO_whenCreeateEmployee_thenReturnEmployeeDTO() throws Exception {

        //Given - precondition or setup
        given(employeeService.saveEmployee(ArgumentMatchers.any(EmployeeDTO.class))).willAnswer(invocation -> invocation.getArgument(0));

        //When - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)));


        //Then - assertion or verification

        response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(employeeDTO.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(employeeDTO.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employeeDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(employeeDTO.getEmail())));

    }

    @Test
    @DisplayName("Given Employee List When Find All Employee  Then Return Page EmployeeDTO ")
    public void givenEmployeeList_whenFindAllEmployee_thenReturnEmployeeDTOList() throws Exception {

        //Given - precondition or setup
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<EmployeeDTO> employeeDTOList = EmployeeMapper.INSTANCE.toDTOList(getEmployees());
        Page<EmployeeDTO> employeeDTOPage = new PageImpl<>(employeeDTOList, pageRequest, employeeDTOList.size());

        given(employeeService.findAllEmployees(ArgumentMatchers.any())).willReturn(employeeDTOPage);

        //When - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/employees"));

        //Then - assertion or verification
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(employeeDTOList.size())))
                .andExpect(jsonPath("$.totalElements", is(employeeDTOList.size())));

    }

    @Test
    @DisplayName("Given Employee ID  When Find By Id  Then Return EmployeeDTO ")
    public void givenEmployeeId_whenFindById_thenReturnEmployeeDTO() throws Exception {

        //Given - precondition or setup
        given(employeeService.findEmployeeById(ArgumentMatchers.any(UUID.class))).willReturn(employeeDTO);

        //When - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employeeDTO.getId()));

        //Then - assertion or verification
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(employeeDTO.getId().toString())));
    }

    @Test
    @DisplayName("Given EmployeeDTO When Update Then Return EmployeeDTO ")
    public void givenEmployeeDTO_whenUpdateEmployee_thenReturnEmployeeDTO() throws Exception {

        //Given - precondition or setup

        String firstName = faker.name().firstName();

        employeeDTO.setFirstName(firstName);
        given(employeeService.updateEmployee(ArgumentMatchers.any(EmployeeDTO.class))).willAnswer(invocation -> invocation.getArgument(0));

        //When - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}", employeeDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)));

        //Then - assertion or verification
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(firstName)));
    }

    @Test
    @DisplayName("Given Employee ID When Delete Then Return Void")
    public void givenEmployeeId_whenDeleteEmployee_thenReturnVoid() throws Exception {

        //Given - precondition or setup

        willDoNothing().given(employeeService).deleteEmployee(ArgumentMatchers.any(UUID.class));

        //When - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/employees/{id}", employeeDTO.getId()));

        //Then - assertion or verification
        response
                .andExpect(status().isNoContent())
                .andDo(print());
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
