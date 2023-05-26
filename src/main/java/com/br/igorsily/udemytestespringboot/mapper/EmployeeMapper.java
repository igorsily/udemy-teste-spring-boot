package com.br.igorsily.udemytestespringboot.mapper;


import com.br.igorsily.udemytestespringboot.dto.v1.EmployeeDTO;
import com.br.igorsily.udemytestespringboot.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO toDTO(Employee Employee);

    Employee toEntity(EmployeeDTO EmployeeDTO);

    List<EmployeeDTO> toDTOList(List<Employee> EmployeeList);

    List<Employee> toEntityList(List<EmployeeDTO> EmployeeDTOList);
}
