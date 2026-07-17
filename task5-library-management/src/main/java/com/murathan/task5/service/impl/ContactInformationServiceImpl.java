package com.murathan.task5.service.impl;

import com.murathan.task5.entity.ContactInformation;
import com.murathan.task5.entity.User;
import com.murathan.task5.repository.ContactInformationRepository;
import com.murathan.task5.repository.UserRepository;
import com.murathan.task5.service.ContactInformationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContactInformationServiceImpl implements ContactInformationService {
    private final ContactInformationRepository contactInformationRepository;
    private final UserRepository userRepository;

    public ContactInformationServiceImpl(ContactInformationRepository contactInformationRepository, UserRepository userRepository) {
        this.contactInformationRepository = contactInformationRepository;
        this.userRepository = userRepository;
    }

    public ContactInformation create(ContactInformation contactinformation) {
        contactinformation.setUser(findUser(contactinformation));
        return contactInformationRepository.save(contactinformation);
    }

    public List<ContactInformation> findAll() {
        return contactInformationRepository.findAll();
    }

    public ContactInformation findById(Long id) {
        return contactInformationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Contact information not found: " + id));
    }

    public ContactInformation update(Long id, ContactInformation contactinformation) {
        ContactInformation existing = findById(id);
        existing.setPhoneNumber(contactinformation.getPhoneNumber());
        existing.setAlternativeEmail(contactinformation.getAlternativeEmail());
        existing.setUser(findUser(contactinformation));
        return contactInformationRepository.save(existing);
    }

    public void delete(Long id) {
        contactInformationRepository.delete(findById(id));
    }

    private User findUser(ContactInformation contactInformation) {
        if (contactInformation.getUser() == null || contactInformation.getUser().getId() == null) {
            throw new IllegalArgumentException("Contact information must include user.id");
        }
        return userRepository.findById(contactInformation.getUser().getId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + contactInformation.getUser().getId()));
    }
}