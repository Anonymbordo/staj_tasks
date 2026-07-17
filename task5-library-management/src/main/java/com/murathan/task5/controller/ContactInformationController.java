package com.murathan.task5.controller;

import com.murathan.task5.entity.ContactInformation;
import com.murathan.task5.service.ContactInformationService;
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
@RequestMapping("/api/contact-informations")
public class ContactInformationController {
    private final ContactInformationService contactInformationService;

    public ContactInformationController(ContactInformationService contactInformationService) {
        this.contactInformationService = contactInformationService;
    }

    @PostMapping
    public ResponseEntity<ContactInformation> create(@RequestBody ContactInformation contactInformation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactInformationService.create(contactInformation));
    }

    @GetMapping
    public ResponseEntity<List<ContactInformation>> findAll() {
        return ResponseEntity.ok(contactInformationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactInformation> findById(@PathVariable Long id) {
        return ResponseEntity.ok(contactInformationService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactInformation> update(@PathVariable Long id, @RequestBody ContactInformation contactInformation) {
        return ResponseEntity.ok(contactInformationService.update(id, contactInformation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactInformationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}