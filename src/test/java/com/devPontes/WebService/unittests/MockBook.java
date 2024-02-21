package com.devPontes.WebService.unittests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.devPontes.WebService.DTO.BookDTO;
import com.devPontes.WebService.model.entities.Book;

public class MockBook extends Book {
    private static final long serialVersionUID = 1L;

	public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDTO mockVO() {
        return mockVO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> Books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            Books.add(mockEntity(i));
        }
        return Books;
    }

    public List<BookDTO> mockVOList() {
        List<BookDTO> Books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Books.add(mockVO(i));
        }
        return Books;
    }
    
    public Book mockEntity(Integer number) {
        Book Book = new Book();
        Book.setAuthor("Some Author" + number);
        Book.setTitle("Some Title" + number);
        Book.setPrice(25D);
        Book.setId(number.longValue());
        Book.setLaunchDate(new Date());
        return Book;
    }

    public BookDTO mockVO(Integer number) {
        BookDTO book = new BookDTO();
        book.setId(number.longValue());
        book.setAuthor("Some Author" + number);
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        book.setTitle("Some Title" + number);
        return book;
    }

}

