package com.carspareparts.system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carspareparts.system.domain.IssuedPart;

public interface IssuedPartRepository extends JpaRepository<IssuedPart, UUID> {

    // E2: all issued parts for a customer across all their part orders, newest issuedDate first
    @Query("SELECT ip FROM IssuedPart ip WHERE UPPER(ip.partOrder.customer.customerCode) = UPPER(:customerCode) ORDER BY ip.issuedDate DESC")
    List<IssuedPart> findByCustomerCode(@Param("customerCode") String customerCode);

    // E3: check if a part order has any issued parts (used to protect PartOrder from deletion)
    boolean existsByPartOrder_Id(UUID partOrderId);
}
