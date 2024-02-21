package com.devPontes.WebService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devPontes.WebService.model.entities.Book;
import com.devPontes.WebService.model.entities.Person;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {}
