package com.carspareparts.system.repository;

import com.carspareparts.system.domain.PartOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PartOrderRepository extends JpaRepository<PartOrder, UUID> {}