package com.devPontes.WebService.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.devPontes.WebService.DTO.BookDTO;
import com.devPontes.WebService.exceptions.RequiredObjectIsNullException;
import com.devPontes.WebService.model.entities.Book;
import com.devPontes.WebService.repositories.BookRepository;
import com.devPontes.WebService.repositories.BookRepository;
import com.devPontes.WebService.services.BookService;
import com.devPontes.WebService.services.BookService;
import com.devPontes.WebService.unittests.MockBook;
import com.devPontes.WebService.unittests.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

	MockBook input;

	@InjectMocks
	private BookService service;

	@Mock
	BookRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
	List<Book> list = input.mockEntityList(); 
		
		when(repository.findAll()).thenReturn(list);
		
		var books = service.findAll();
		
		assertNotNull(books);
		assertEquals(14, books.size());
		
		var BookOne = books.get(1);
		
		assertNotNull(BookOne);
		assertNotNull(BookOne.getId());
		assertNotNull(BookOne.getLinks());
		
		assertTrue(BookOne.hasLinks());
		assertEquals("Some Author1", BookOne.getAuthor());
		assertEquals("Some Title1", BookOne.getTitle());
		assertNotNull(BookOne.getLaunchDate());
		assertEquals(25D, BookOne.getPrice());
		
		var BookFour = books.get(4);
		
		assertNotNull(BookFour);
		assertNotNull(BookFour.getId());
		assertNotNull(BookFour.getLinks());
		
		assertTrue(BookOne.hasLinks());
		assertEquals("Some Author1", BookOne.getAuthor());
		assertEquals("Some Title1", BookOne.getTitle());
		assertNotNull(BookOne.getLaunchDate());
		assertEquals(25D, BookOne.getPrice());
		
		var BookSeven = books.get(7);
		
		assertNotNull(BookSeven);
		assertNotNull(BookSeven.getId());
		assertNotNull(BookSeven.getLinks());
		
		assertTrue(BookOne.hasLinks());
		assertEquals("Some Author1", BookOne.getAuthor());
		assertEquals("Some Title1", BookOne.getTitle());
		assertNotNull(BookOne.getLaunchDate());
		assertEquals(25D, BookOne.getPrice());
	}

	@Test
	void testFindById() throws Exception {
		Book entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());

		assertTrue(result.hasLinks());
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertNotNull(result.getLaunchDate());
		assertEquals(25D, result.getPrice());
	}

	@Test
	void testCreate() throws Exception {
		Book entity = input.mockEntity(1);
		entity.setId(1L);

		Book persisted = entity;
		persisted.setId(1L);

		BookDTO vo = input.mockVO(1);
		vo.setId(1L);

		when(repository.save(entity)).thenReturn(persisted);

		var result = service.createBookDTO(vo);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());

		assertTrue(result.hasLinks());
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertNotNull(result.getLaunchDate());
		assertEquals(25D, result.getPrice());
	}

	@Test
	void testUpdate() throws Exception {
		Book entity = input.mockEntity(1);
		entity.setId(1L);

		Book persisted = entity;
		persisted.setId(1L);

		BookDTO vo = input.mockVO(1);
		vo.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = service.updateBookDTO(vo);

		assertTrue(result.hasLinks());
		assertNotNull(result);
		assertNotNull(result.getId());
		assertNotNull(result.getLinks());
		assertTrue(result.hasLinks());
		assertEquals("Some Author1", result.getAuthor());
		assertEquals("Some Title1", result.getTitle());
		assertNotNull(result.getLaunchDate());
		assertEquals(25D, result.getPrice());

	}

	@Test
	void testDelete() {
		Book entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		service.delete(1L);
	}

	@Test
	void testUpdateCreateNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.createBookDTO(null);
		});

		String expected = "Its is not allowed to persist a null object";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expected));
	}

	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateBookDTO(null);
		});

		String expectedMessage = "Its is not allowed to persist a null object";
		String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
		
		assertTrue(actualMessage.contains(expectedMessage));
	}

}