package com.devPontes.WebService.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.devPontes.WebService.DTO.PersonDTO;
import com.devPontes.WebService.model.entities.Person;

public class MyMapper {

	private static ModelMapper mapper = new ModelMapper();
	static {
		mapper.createTypeMap(Person.class, PersonDTO.class)
		.addMapping(Person::getId, PersonDTO::setId);
	}

	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}

	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<>();
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}

}
