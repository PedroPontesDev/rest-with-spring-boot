package com.devPontes.WebService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devPontes.WebService.DTO.PersonDTO;
import com.devPontes.WebService.services.PersonService;



@RestController
@RequestMapping(value = "/people")
public class PersonController {

	@Autowired
	private PersonService services;
	
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonDTO findById(@PathVariable(value = "id") Long id) throws Exception {
		return services.findById(id);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PersonDTO> findAll()  {
		return services.findAll();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonDTO createPersonDTO(@RequestBody PersonDTO PersonDTO)  {
		return services.createPersonDTO(PersonDTO);
	}
	
	@PutMapping (consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonDTO updatePersonDTO(@RequestBody PersonDTO PersonDTO)  {
		return services.updatePersonDTO(PersonDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?>deletePersonDTO(@PathVariable Long id)  {
		services.delete(id);
	    return ResponseEntity.noContent().build();
	}
}
