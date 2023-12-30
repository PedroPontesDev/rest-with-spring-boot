package com.devPontes.WebService.services;



import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devPontes.WebService.DTO.PersonDTO;
import com.devPontes.WebService.exceptions.ResourceNotFoundException;
import com.devPontes.WebService.mapper.DozerMapper;
import com.devPontes.WebService.model.entities.Person;
import com.devPontes.WebService.repositories.PersonRepository;




@Service
public class PersonService {

	@Autowired
	PersonRepository repository;
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());

	
	public List<PersonDTO> findAll() {
		return DozerMapper.parseListObjects(repository.findAll(), PersonDTO.class);
	}

	public PersonDTO findById(Long id) {
		logger.info("Finding one PersonDTO!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
	    return DozerMapper.parseObject(entity, PersonDTO.class);
	}
	public PersonDTO createPersonDTO(PersonDTO personDTO) {
		logger.info("Creating PersonDTO!");		
		var entity = DozerMapper.parseObject(personDTO, Person.class);
		var dto =  DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
	    return dto;
	}
	
	public PersonDTO updatePersonDTO(PersonDTO PersonDTO) {
		logger.info("Updating PersonDTO!");
	
		var entity = repository.findById(PersonDTO.getId()) // Todas requisições PUT primeiro tenho que buscar no banco e atualizar e depois gravar
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
	
		entity.setFirstName(PersonDTO.getFirstName());
		entity.setLasName(PersonDTO.getLastName());
		entity.setAddress(PersonDTO.getAddress());
		entity.setGender(PersonDTO.getGender());
		
		var dto =  DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
	    return dto;
	}
	
	public void delete(Long id) {
		logger.info("Deleting PersonDTO!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		repository.delete(entity);
		
	}

}
