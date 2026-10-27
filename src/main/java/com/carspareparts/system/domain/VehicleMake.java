package com.carspareparts.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vehicle_make")
public class VehicleMake {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany(mappedBy = "vehicleMakes")
    @JsonIgnore // Prevents infinite JSON loop as required
    private List<Storekeeper> storekeepers;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Storekeeper> getStorekeepers() { return storekeepers; }
    public void setStorekeepers(List<Storekeeper> storekeepers) { this.storekeepers = storekeepers; }
}