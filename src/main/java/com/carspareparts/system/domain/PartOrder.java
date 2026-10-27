package com.carspareparts.system.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "part_order")
public class PartOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private PartOrderStatus status;

    private String carModel;
    private int modelYear;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "storekeeper_id")
    private Storekeeper storekeeper;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }
    public PartOrderStatus getStatus() { return status; }
    public void setStatus(PartOrderStatus status) { this.status = status; }
    public String getCarModel() { return carModel; }
    public void setCarModel(String carModel) { this.carModel = carModel; }
    public int getModelYear() { return modelYear; }
    public void setModelYear(int modelYear) { this.modelYear = modelYear; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Storekeeper getStorekeeper() { return storekeeper; }
    public void setStorekeeper(Storekeeper storekeeper) { this.storekeeper = storekeeper; }
}