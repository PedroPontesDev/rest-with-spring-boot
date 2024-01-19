package com.devPontes.WebService.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devPontes.WebService.data.DTO.v1.PersonDTO;
import com.devPontes.WebService.data.DTO.v2.PersonDTOv2;
import com.devPontes.WebService.exceptions.ResourceNotFoundException;
import com.devPontes.WebService.mapper.MyMapper;
import com.devPontes.WebService.mapper.custom.PersonMapper;
import com.devPontes.WebService.model.entities.Person;
import com.devPontes.WebService.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;
	
	@Autowired 
	PersonMapper mapper;

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	public List<PersonDTO> findAll() {
		return MyMapper.parseListObjects(repository.findAll(), PersonDTO.class);
	}

	public PersonDTO findById(Long id) {
		logger.info("Finding one PersonDTO!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		return MyMapper.parseObject(entity, PersonDTO.class);
	}

	public PersonDTO createPersonDTO(PersonDTO personDTO) {
		logger.info("Creating PersonDTO!");
		var entity = MyMapper.parseObject(personDTO, Person.class);
		var dto = MyMapper.parseObject(repository.save(entity), PersonDTO.class);
		return dto;
	}

	public PersonDTO updatePersonDTO(PersonDTO PersonDTO) {
		logger.info("Updating PersonDTO!");

		var entity = repository.findById(PersonDTO.getId()) // Todas requisições PUT primeiro tenho que buscar no banco
															// e atualizar e depois gravar
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));

		entity.setFirstName(PersonDTO.getFirstName());
		entity.setLastName(PersonDTO.getLastName());
		entity.setAddress(PersonDTO.getAddress());
		entity.setGender(PersonDTO.getGender());

		var dto = MyMapper.parseObject(repository.save(entity), PersonDTO.class);
		return dto;
	}

	public void delete(Long id) {
		logger.info("Deleting PersonDTO!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		repository.delete(entity);

	}

	public PersonDTOv2 createPersonDTOv2(PersonDTOv2 personDTOv2) {
		var entity = MyMapper.parseObject(personDTOv2, Person.class);
		var dto = MyMapper.parseObject(repository.save(entity), PersonDTOv2.class);
		return dto;
	}

}
