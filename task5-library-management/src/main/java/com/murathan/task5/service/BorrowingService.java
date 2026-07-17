package com.murathan.task5.service;

import com.murathan.task5.entity.Borrowing;

import java.util.List;

public interface BorrowingService {
    Borrowing create(Borrowing borrowing);

    List<Borrowing> findAll();

    Borrowing findById(Long id);

    Borrowing update(Long id, Borrowing borrowing);

    void delete(Long id);
}