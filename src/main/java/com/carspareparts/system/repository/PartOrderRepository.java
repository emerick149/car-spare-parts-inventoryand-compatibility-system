package com.carspareparts.system.repository;

import com.carspareparts.system.domain.PartOrder;
import com.carspareparts.system.domain.PartOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PartOrderRepository extends JpaRepository<PartOrder, UUID> {
    List<PartOrder> findByStatusOrderByOrderDateAsc(PartOrderStatus status);
    List<PartOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);
}