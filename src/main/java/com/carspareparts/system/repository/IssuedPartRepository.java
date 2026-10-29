package com.carspareparts.system.repository;

import com.carspareparts.system.domain.IssuedPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface IssuedPartRepository extends JpaRepository<IssuedPart, UUID> {

    // E2: all issued parts for a customer across all part orders, newest issuedDate first
    @Query("SELECT ip FROM IssuedPart ip WHERE UPPER(ip.partOrder.customer.customerCode) = UPPER(:customerCode) ORDER BY ip.issuedDate DESC")
    List<IssuedPart> findByCustomerCode(@Param("customerCode") String customerCode);

    // E3: check if a part order has any issued parts (to protect against deletion)
    boolean existsByPartOrder_Id(UUID partOrderId);
}
