package com.murathan.task5.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact_informations")
public class ContactInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String phoneNumber;

    @Column(length = 120)
    private String alternativeEmail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnoreProperties({"address", "contactInformation", "borrowings"})
    private User user;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAlternativeEmail() { return alternativeEmail; }
    public void setAlternativeEmail(String alternativeEmail) { this.alternativeEmail = alternativeEmail; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}