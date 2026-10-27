package com.carspareparts.system.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "store_section")
public class StoreSection {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int sectionNumber;
    private String name;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public int getSectionNumber() { return sectionNumber; }
    public void setSectionNumber(int sectionNumber) { this.sectionNumber = sectionNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}