package com.murathan.task5.service.impl;

import com.murathan.task5.entity.Book;
import com.murathan.task5.entity.Borrowing;
import com.murathan.task5.entity.User;
import com.murathan.task5.repository.BookRepository;
import com.murathan.task5.repository.BorrowingRepository;
import com.murathan.task5.repository.UserRepository;
import com.murathan.task5.service.BorrowingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BorrowingServiceImpl implements BorrowingService {
    private final BorrowingRepository borrowingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BorrowingServiceImpl(BorrowingRepository borrowingRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.borrowingRepository = borrowingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public Borrowing create(Borrowing borrowing) {
        borrowing.setUser(findUser(borrowing));
        Book book = findBook(borrowing);
        borrowing.setBook(book);
        if (borrowing.getBorrowDate() == null) {
            borrowing.setBorrowDate(LocalDate.now());
        }
        if (borrowing.getDueDate() == null) {
            borrowing.setDueDate(borrowing.getBorrowDate().plusDays(14));
        }
        if (borrowing.getStatus() == null || borrowing.getStatus().isBlank()) {
            borrowing.setStatus("BORROWED");
        }
        book.setAvailable(false);
        bookRepository.save(book);
        return borrowingRepository.save(borrowing);
    }

    public List<Borrowing> findAll() {
        return borrowingRepository.findAll();
    }

    public Borrowing findById(Long id) {
        return borrowingRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Borrowing not found: " + id));
    }

    public Borrowing update(Long id, Borrowing borrowing) {
        Borrowing existing = findById(id);
        existing.setUser(findUser(borrowing));
        existing.setBook(findBook(borrowing));
        existing.setBorrowDate(borrowing.getBorrowDate());
        existing.setDueDate(borrowing.getDueDate());
        existing.setReturnDate(borrowing.getReturnDate());
        existing.setStatus(borrowing.getStatus());
        if ("RETURNED".equalsIgnoreCase(borrowing.getStatus())) {
            Book book = existing.getBook();
            book.setAvailable(true);
            bookRepository.save(book);
        }
        return borrowingRepository.save(existing);
    }

    public void delete(Long id) {
        borrowingRepository.delete(findById(id));
    }

    private User findUser(Borrowing borrowing) {
        if (borrowing.getUser() == null || borrowing.getUser().getId() == null) {
            throw new IllegalArgumentException("Borrowing must include user.id");
        }
        return userRepository.findById(borrowing.getUser().getId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + borrowing.getUser().getId()));
    }

    private Book findBook(Borrowing borrowing) {
        if (borrowing.getBook() == null || borrowing.getBook().getId() == null) {
            throw new IllegalArgumentException("Borrowing must include book.id");
        }
        return bookRepository.findById(borrowing.getBook().getId())
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + borrowing.getBook().getId()));
    }
}