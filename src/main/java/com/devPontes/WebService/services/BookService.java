package com.devPontes.WebService.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devPontes.WebService.DTO.BookDTO;
import com.devPontes.WebService.controllers.BookController;
import com.devPontes.WebService.controllers.PersonController;
import com.devPontes.WebService.exceptions.RequiredObjectIsNullException;
import com.devPontes.WebService.exceptions.ResourceNotFoundException;
import com.devPontes.WebService.mapper.MyMapper;
import com.devPontes.WebService.model.entities.Book;
import com.devPontes.WebService.model.entities.Person;
import com.devPontes.WebService.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository repository;

	private Logger logger = Logger.getLogger(BookService.class.getName());

	public List<BookDTO> findAll() {
		var books = MyMapper.parseListObjects(repository.findAll(), BookDTO.class);
		books.forEach(p -> p.add(linkTo(methodOn(BookController.class).findAll()).withSelfRel()));
		return books;

	}

	public BookDTO findById(Long id) throws Exception {
		logger.info("Finding one BookDTO!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		var dto = MyMapper.parseObject(entity, BookDTO.class);
		// Escolha qual metodo do controller sera atribuido os links HATEOAS
		dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return dto; 
		// Após fazer o dto exteneder RepresentationModel, você já é capaz de add links
		// de HATEOAS
	}

	public BookDTO createBookDTO(BookDTO BookDTO) throws Exception {
		if (BookDTO == null)
			throw new RequiredObjectIsNullException();
		logger.info("Creating BookDTO!");
		var entity = MyMapper.parseObject(BookDTO, Book.class);
		var dto = MyMapper.parseObject(repository.save(entity), BookDTO.class);
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel());
		return dto;
	}  

	public BookDTO updateBookDTO(BookDTO BookDTO) throws Exception {
		if (BookDTO == null)
			throw new RequiredObjectIsNullException();
		logger.info("Updating BookDTO!");
		var entity = repository.findById(BookDTO.getId()) // Todas requisições PUT primeiro tenho que buscar no banco
															// e atualizar e depois gravar
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));

		entity.setAuthor(BookDTO.getAuthor());
		entity.setPrice(BookDTO.getPrice());
		entity.setTitle(BookDTO.getTitle());
		entity.setLaunchDate(BookDTO.getLaunchDate());

		var dto = MyMapper.parseObject(repository.save(entity), BookDTO.class);
		dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel());
		return dto;
	}

	public void delete(Long id) {
		logger.info("Deleting BookDTO!");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		repository.delete(entity);

	}

}
