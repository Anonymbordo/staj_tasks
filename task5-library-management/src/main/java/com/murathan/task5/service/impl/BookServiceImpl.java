package com.murathan.task5.service.impl;

import com.murathan.task5.entity.Book;
import com.murathan.task5.repository.BookRepository;
import com.murathan.task5.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book create(Book book) {
        if (book.getAvailable() == null) {
            book.setAvailable(true);
        }
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Book not found: " + id));
    }

    public Book update(Long id, Book book) {
        Book existing = findById(id);
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setIsbn(book.getIsbn());
        existing.setPublisher(book.getPublisher());
        existing.setAvailable(book.getAvailable());
        return bookRepository.save(existing);
    }

    public void delete(Long id) {
        bookRepository.delete(findById(id));
    }
}