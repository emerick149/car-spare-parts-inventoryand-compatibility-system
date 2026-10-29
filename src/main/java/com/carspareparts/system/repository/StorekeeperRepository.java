package com.carspareparts.system.repository;

import com.carspareparts.system.domain.Storekeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface StorekeeperRepository extends JpaRepository<Storekeeper, UUID> {

    // C1: storekeepers who hold a vehicle make matched case-insensitively
    @Query("SELECT s FROM Storekeeper s JOIN s.vehicleMakes vm WHERE LOWER(vm.name) = LOWER(:name)")
    List<Storekeeper> findByVehicleMakeName(@Param("name") String name);
}