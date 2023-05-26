package com.br.igorsily.udemycursospringboot.mappers;

import com.br.igorsily.udemycursospringboot.dtos.v1.PersonDTO;
import com.br.igorsily.udemycursospringboot.entitys.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO toDTO(Person person);

    Person toEntity(PersonDTO personDTO);

    List<PersonDTO> toDTOList(List<Person> personList);

    List<Person> toEntityList(List<PersonDTO> personDTOList);
}
