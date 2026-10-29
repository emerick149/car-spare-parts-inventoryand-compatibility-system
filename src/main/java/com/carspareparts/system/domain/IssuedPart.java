package com.carspareparts.system.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "issued_part")
public class IssuedPart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String partName;
    private String remarks;
    private int quantity;
    private LocalDate issuedDate;

    // Include full partOrder in JSON response, but break the Storekeeper -> VehicleMake loop
    @JsonIgnoreProperties({"storekeeper"})
    @ManyToOne
    @JoinColumn(name = "part_order_id")
    private PartOrder partOrder;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getPartName() { return partName; }
    public void setPartName(String partName) { this.partName = partName; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }
    public PartOrder getPartOrder() { return partOrder; }
    public void setPartOrder(PartOrder partOrder) { this.partOrder = partOrder; }
}
