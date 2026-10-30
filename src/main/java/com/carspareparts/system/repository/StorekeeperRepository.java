package com.carspareparts.system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carspareparts.system.domain.Storekeeper;

public interface StorekeeperRepository extends JpaRepository<Storekeeper, UUID> {

    // C1: storekeepers who hold a vehicle make matched case-insensitively
    @Query("SELECT s FROM Storekeeper s JOIN s.vehicleMakes vm WHERE LOWER(vm.name) = LOWER(:name)")
    List<Storekeeper> findByVehicleMakeName(@Param("name") String name);

    // D1: check if a storekeeper already has a non-VOIDED part order on a given date
    @Query("SELECT COUNT(po) > 0 FROM PartOrder po WHERE po.storekeeper.id = :storekeeperID AND po.orderDate = :orderDate AND po.status <> com.carspareparts.system.domain.PartOrderStatus.VOIDED")
    boolean hasNonVoidedOrderOnDate(@Param("storekeeperID") UUID storekeeperID, @Param("orderDate") LocalDate orderDate);

    // D4: check if a store section is already occupied by another storekeeper
    boolean existsByStoreSection_SectionNumber(int sectionNumber);
}
