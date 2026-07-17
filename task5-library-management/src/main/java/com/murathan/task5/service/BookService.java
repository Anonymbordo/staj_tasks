package com.murathan.task5.service;

import com.murathan.task5.entity.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);

    List<Book> findAll();

    Book findById(Long id);

    Book update(Long id, Book book);

    void delete(Long id);
}