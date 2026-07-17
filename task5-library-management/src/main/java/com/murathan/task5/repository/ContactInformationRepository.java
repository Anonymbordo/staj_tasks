package com.murathan.task5.repository;

import com.murathan.task5.entity.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactInformationRepository extends JpaRepository<ContactInformation, Long> {
}