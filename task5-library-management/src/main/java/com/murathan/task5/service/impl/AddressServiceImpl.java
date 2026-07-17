package com.murathan.task5.service.impl;

import com.murathan.task5.entity.Address;
import com.murathan.task5.entity.User;
import com.murathan.task5.repository.AddressRepository;
import com.murathan.task5.repository.UserRepository;
import com.murathan.task5.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address create(Address address) {
        address.setUser(findUser(address));
        return addressRepository.save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Address not found: " + id));
    }

    public Address update(Long id, Address address) {
        Address existing = findById(id);
        existing.setStreet(address.getStreet());
        existing.setCity(address.getCity());
        existing.setDistrict(address.getDistrict());
        existing.setPostalCode(address.getPostalCode());
        existing.setUser(findUser(address));
        return addressRepository.save(existing);
    }

    public void delete(Long id) {
        addressRepository.delete(findById(id));
    }

    private User findUser(Address address) {
        if (address.getUser() == null || address.getUser().getId() == null) {
            throw new IllegalArgumentException("Address must include user.id");
        }
        return userRepository.findById(address.getUser().getId())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + address.getUser().getId()));
    }
}