package com.carspareparts.system.repository;

import com.carspareparts.system.domain.VehicleMake;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VehicleMakeRepository extends JpaRepository<VehicleMake, UUID> {}