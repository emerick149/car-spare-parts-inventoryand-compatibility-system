package com.carspareparts.system.domain;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "storekeeper")
public class Storekeeper {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "store_section_id")
    private StoreSection storeSection;

    @ManyToMany
    @JoinTable(
            name = "storekeeper_vehicle_make",
            joinColumns = @JoinColumn(name = "storekeeper_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_make_id")
    )
    private List<VehicleMake> vehicleMakes;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public StoreSection getStoreSection() { return storeSection; }
    public void setStoreSection(StoreSection storeSection) { this.storeSection = storeSection; }
    public List<VehicleMake> getVehicleMakes() { return vehicleMakes; }
    public void setVehicleMakes(List<VehicleMake> vehicleMakes) { this.vehicleMakes = vehicleMakes; }
}