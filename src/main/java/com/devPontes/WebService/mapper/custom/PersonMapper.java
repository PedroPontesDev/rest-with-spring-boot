package com.devPontes.WebService.mapper.custom;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.devPontes.WebService.data.DTO.v2.PersonDTOv2;
import com.devPontes.WebService.model.entities.Person;

@Service
public class PersonMapper {

	public PersonDTOv2 convertEntityToDto(Person person) {
		PersonDTOv2 dto = new PersonDTOv2();
		dto.setId(person.getId());
		dto.setAddress(person.getAddress());
	    dto.setBirthDate(new Date());
	    dto.setFirstName(person.getFirstName());
	    dto.setLastName(person.getLastName());
	    dto.setGender(person.getGender());
	    return dto;
	    
	}
	
	public Person convertDtoToEntity(PersonDTOv2 dto) {
		Person entity = new Person();
	    entity.setId(dto.getId());
		entity.setAddress(dto.getAddress());
	   // entity.setBirthDate(new Date());
	    entity.setFirstName(dto.getFirstName());
	    entity.setLastName(dto.getLastName());
	    entity.setGender(dto.getGender());
	    return entity;
	}
	
}
