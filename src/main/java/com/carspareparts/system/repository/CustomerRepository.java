package com.carspareparts.system.repository;

import com.carspareparts.system.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    // B1: find customers by last name, case-insensitive, sorted A→Z by first name
    List<Customer> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // C4: distinct customers who have at least one part order with the given storekeeper
    @Query("SELECT DISTINCT po.customer FROM PartOrder po WHERE po.storekeeper.id = :storekeeperID")
    List<Customer> findDistinctCustomersByStorekeeperId(@Param("storekeeperID") UUID storekeeperID);
}