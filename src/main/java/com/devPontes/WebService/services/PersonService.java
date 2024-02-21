package com.devPontes.WebService.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.devPontes.WebService.DTO.PersonDTO;
import com.devPontes.WebService.controllers.PersonController;
import com.devPontes.WebService.exceptions.RequiredObjectIsNullException;
import com.devPontes.WebService.exceptions.ResourceNotFoundException;
import com.devPontes.WebService.mapper.MyMapper;
import com.devPontes.WebService.model.entities.Person;
import com.devPontes.WebService.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	private Logger logger = Logger.getLogger(PersonService.class.getName());

	public List<PersonDTO> findAll() {
		var persons = MyMapper.parseListObjects(repository.findAll(), PersonDTO.class);
		persons.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findAll()).withSelfRel()));
		return persons;

	}

	public PersonDTO findById(Long id) throws Exception {
		logger.info("Finding one PersonDTO!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		var dto = MyMapper.parseObject(entity, PersonDTO.class);
		// Escolha qual metodo do controller sera atribuido os links HATEOAS
		dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return dto;
		// Após fazer o dto exteneder RepresentationModel, você já é capaz de add links
	   // de HATEOAS

	}

	public PersonDTO createPersonDTO(PersonDTO personDTO) throws Exception {
		if (personDTO == null)
			throw new RequiredObjectIsNullException();
		logger.info("Creating PersonDTO!");
		var entity = MyMapper.parseObject(personDTO, Person.class);
		var dto = MyMapper.parseObject(repository.save(entity), PersonDTO.class);
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel());
		return dto;
	}  

	public PersonDTO updatePersonDTO(PersonDTO PersonDTO) throws Exception {
		if (PersonDTO == null)
			throw new RequiredObjectIsNullException();
		logger.info("Updating PersonDTO!");
		var entity = repository.findById(PersonDTO.getId()) // Todas requisições PUT primeiro tenho que buscar no banco
															// e atualizar e depois gravar
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));

		entity.setFirstName(PersonDTO.getFirstName());
		entity.setLastName(PersonDTO.getLastName());
		entity.setAddress(PersonDTO.getAddress());
		entity.setGender(PersonDTO.getGender());

		var dto = MyMapper.parseObject(repository.save(entity), PersonDTO.class);
		dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel());
		return dto;
	}

	public void delete(Long id) {
		logger.info("Deleting PersonDTO!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		repository.delete(entity);

	}

}
