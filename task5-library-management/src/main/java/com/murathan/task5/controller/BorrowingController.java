package com.murathan.task5.controller;

import com.murathan.task5.entity.Borrowing;
import com.murathan.task5.service.BorrowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {
    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    @PostMapping
    public ResponseEntity<Borrowing> create(@RequestBody Borrowing borrowing) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingService.create(borrowing));
    }

    @GetMapping
    public ResponseEntity<List<Borrowing>> findAll() {
        return ResponseEntity.ok(borrowingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Borrowing> findById(@PathVariable Long id) {
        return ResponseEntity.ok(borrowingService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Borrowing> update(@PathVariable Long id, @RequestBody Borrowing borrowing) {
        return ResponseEntity.ok(borrowingService.update(id, borrowing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        borrowingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}