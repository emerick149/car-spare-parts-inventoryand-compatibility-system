package com.carspareparts.system.repository;

import com.carspareparts.system.domain.PartOrder;
import com.carspareparts.system.domain.PartOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PartOrderRepository extends JpaRepository<PartOrder, UUID> {

    // B2
    List<PartOrder> findByStatusOrderByOrderDateAsc(PartOrderStatus status);

    // B3
    List<PartOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);

    // F1: one row per status with its count — statuses with zero orders do not appear
    @Query("SELECT po.status, COUNT(po) FROM PartOrder po GROUP BY po.status")
    List<Object[]> countByStatus();

    // F2: bulk-void all non-FULFILLED orders for a storekeeper on a given date; returns affected row count
    @Modifying
    @Transactional
    @Query("UPDATE PartOrder po SET po.status = com.carspareparts.system.domain.PartOrderStatus.VOIDED " +
           "WHERE po.storekeeper.id = :storekeeperID " +
           "AND po.orderDate = :date " +
           "AND po.status <> com.carspareparts.system.domain.PartOrderStatus.FULFILLED")
    int cancelDayOrders(@Param("storekeeperID") UUID storekeeperID, @Param("date") LocalDate date);
}
