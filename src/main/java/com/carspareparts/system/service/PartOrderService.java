package com.carspareparts.system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.carspareparts.system.domain.PartOrder;
import com.carspareparts.system.domain.PartOrderStatus;
import com.carspareparts.system.repository.IssuedPartRepository;
import com.carspareparts.system.repository.PartOrderRepository;
import com.carspareparts.system.repository.StorekeeperRepository;

@Service
public class PartOrderService {
    @Autowired private PartOrderRepository partOrderRepository;
    @Autowired private StorekeeperRepository storekeeperRepository;
    @Autowired private IssuedPartRepository issuedPartRepository;

    // D1: safe save — orderDate must not be in the past, storekeeper must not have a conflict on that date
    public Object save(PartOrder entity) {
        if (entity.getOrderDate() != null && entity.getOrderDate().isBefore(LocalDate.now())) {
            return Map.entry(400, "Part order date cannot be in the past");
        }
        if (entity.getStorekeeper() != null && entity.getOrderDate() != null) {
            boolean conflict = storekeeperRepository.hasNonVoidedOrderOnDate(
                    entity.getStorekeeper().getId(), entity.getOrderDate());
            if (conflict) {
                return Map.entry(409, "Storekeeper already has a part order on that date");
            }
        }
        entity.setStatus(PartOrderStatus.PLACED);
        partOrderRepository.save(entity);
        return Map.entry(201, "Part order saved successfully");
    }

    // A1 (CRUD): get all part orders
    public List<PartOrder> getAll() { return partOrderRepository.findAll(); }

    // A1 (CRUD): get part order by id
    public Object getById(UUID id) {
        return partOrderRepository.findById(id)
                .map(partOrder -> (Object) partOrder)
                .orElse("The part order with that id does not exist");
    }

    // A1 (CRUD): update part order (status excluded — use D2 endpoint)
    public String update(UUID id, PartOrder newData) {
        return partOrderRepository.findById(id).map(existing -> {
            existing.setOrderDate(newData.getOrderDate());
            existing.setCarModel(newData.getCarModel());
            existing.setModelYear(newData.getModelYear());
            existing.setCustomer(newData.getCustomer());
            existing.setStorekeeper(newData.getStorekeeper());
            partOrderRepository.save(existing);
            return "Part order updated successfully";
        }).orElse("The part order with that id does not exist");
    }

    // D2: status flow — PLACED→PROCESSING|VOIDED, PROCESSING→FULFILLED|VOIDED, FULFILLED/VOIDED→nothing
    public Object updateStatus(UUID id, PartOrderStatus newStatus) {
        return partOrderRepository.findById(id).map(existing -> {
            PartOrderStatus current = existing.getStatus();
            boolean allowed = switch (current) {
                case PLACED      -> newStatus == PartOrderStatus.PROCESSING || newStatus == PartOrderStatus.VOIDED;
                case PROCESSING  -> newStatus == PartOrderStatus.FULFILLED  || newStatus == PartOrderStatus.VOIDED;
                case FULFILLED, VOIDED -> false;
            };
            if (!allowed) {
                return Map.entry(400, "Cannot change status from " + current + " to " + newStatus);
            }
            existing.setStatus(newStatus);
            partOrderRepository.save(existing);
            return Map.entry(200, "Part order status updated successfully");
        }).orElse(Map.entry(404, "The part order with that id does not exist"));
    }

    // B2: get part orders filtered by status, sorted by orderDate ascending
    public List<PartOrder> getByStatus(PartOrderStatus status) {
        return partOrderRepository.findByStatusOrderByOrderDateAsc(status);
    }

    // B3: get part orders in a date range (both dates inclusive), sorted by orderDate
    public List<PartOrder> getByDateRange(LocalDate start, LocalDate end) {
        return partOrderRepository.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
    }

    // F1: part order counts grouped by status — statuses with zero orders do not appear
    public List<Object[]> getStatsByStatus() {
        return partOrderRepository.countByStatus();
    }

    // F2: bulk-void all non-FULFILLED orders for a storekeeper on a given date; returns affected count
    public String cancelDay(UUID storekeeperID, LocalDate date) {
        int count = partOrderRepository.cancelDayOrders(storekeeperID, date);
        return count + " part orders cancelled";
    }

    // Bonus: paginated part orders
    public Page<PartOrder> getPage(Pageable pageable) {
        return partOrderRepository.findAll(pageable);
    }

    // A1 (CRUD) + E3 protected: delete part order — blocked if it has issued parts
    public String delete(UUID id) {
        if (!partOrderRepository.existsById(id)) return "The part order with that id does not exist";
        if (issuedPartRepository.existsByPartOrder_Id(id)) return "That part order has issued parts, delete them first";
        partOrderRepository.deleteById(id);
        return "Part order deleted successfully";
    }
}
