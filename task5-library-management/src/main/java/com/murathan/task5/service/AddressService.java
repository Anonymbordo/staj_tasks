package com.murathan.task5.service;

import com.murathan.task5.entity.Address;

import java.util.List;

public interface AddressService {
    Address create(Address address);

    List<Address> findAll();

    Address findById(Long id);

    Address update(Long id, Address address);

    void delete(Long id);
}