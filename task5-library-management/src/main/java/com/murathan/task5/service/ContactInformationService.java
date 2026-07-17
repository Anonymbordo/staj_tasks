package com.murathan.task5.service;

import com.murathan.task5.entity.ContactInformation;

import java.util.List;

public interface ContactInformationService {
    ContactInformation create(ContactInformation contactInformation);

    List<ContactInformation> findAll();

    ContactInformation findById(Long id);

    ContactInformation update(Long id, ContactInformation contactInformation);

    void delete(Long id);
}