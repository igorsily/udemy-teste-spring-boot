package com.br.igorsily.udemytestespringboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenericMapper<E,D> {

    GenericMapper INSTANCE = Mappers.getMapper(GenericMapper.class);

    E toEntity(D dto);

    D toDTO(E entity);

    List<D> toDTOList(List<E> EmployeeList);

    List<E> toEntityList(List<D> EmployeeDTOList);
}
