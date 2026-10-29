package com.carspareparts.system.repository;

import com.carspareparts.system.domain.VehicleMake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface VehicleMakeRepository extends JpaRepository<VehicleMake, UUID> {

    // C3: vehicle makes that no storekeeper holds (storekeepers collection is empty)
    @Query("SELECT vm FROM VehicleMake vm WHERE vm.storekeepers IS EMPTY")
    List<VehicleMake> findUnused();
}