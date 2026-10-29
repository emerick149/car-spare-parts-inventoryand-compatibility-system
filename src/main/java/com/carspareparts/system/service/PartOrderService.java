package com.carspareparts.system.service;

import com.carspareparts.system.domain.PartOrder;
import com.carspareparts.system.domain.PartOrderStatus;
import com.carspareparts.system.repository.PartOrderRepository;
import com.carspareparts.system.repository.StorekeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PartOrderService {
    @Autowired private PartOrderRepository partOrderRepository;
    @Autowired private StorekeeperRepository storekeeperRepository;

    // D1: safe save with business rule validations
    public Object save(PartOrder entity) {
        // Rule 1: orderDate cannot be in the past
        if (entity.getOrderDate() != null && entity.getOrderDate().isBefore(LocalDate.now())) {
            return Map.entry(400, "Part order date cannot be in the past");
        }
        // Rule 2: storekeeper must not already have a non-VOIDED order on that date
        if (entity.getStorekeeper() != null && entity.getOrderDate() != null) {
            boolean conflict = storekeeperRepository.hasNonVoidedOrderOnDate(
                    entity.getStorekeeper().getId(), entity.getOrderDate());
            if (conflict) {
                return Map.entry(409, "Storekeeper already has a part order on that date");
            }
        }
        // New orders always start as PLACED
        entity.setStatus(PartOrderStatus.PLACED);
        partOrderRepository.save(entity);
        return Map.entry(201, "Part order saved successfully");
    }

    public List<PartOrder> getAll() { return partOrderRepository.findAll(); }

    public Object getById(UUID id) {
        return partOrderRepository.findById(id)
                .map(partOrder -> (Object) partOrder)
                .orElse("The part order with that id does not exist");
    }

    public String update(UUID id, PartOrder newData) {
        return partOrderRepository.findById(id).map(existing -> {
            existing.setOrderDate(newData.getOrderDate());
            existing.setCarModel(newData.getCarModel());
            existing.setModelYear(newData.getModelYear());
            existing.setCustomer(newData.getCustomer());
            existing.setStorekeeper(newData.getStorekeeper());
            // NOTE: We intentionally DO NOT update the status here. Part D requires status to only change via a specific endpoint.
            partOrderRepository.save(existing);
            return "Part order updated successfully";
        }).orElse("The part order with that id does not exist");
    }

    // D2: status flow with allowed transition validation
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

    public List<PartOrder> getByStatus(PartOrderStatus status) {
        return partOrderRepository.findByStatusOrderByOrderDateAsc(status);
    }

    public List<PartOrder> getByDateRange(LocalDate start, LocalDate end) {
        return partOrderRepository.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
    }

    public String delete(UUID id) {
        if (!partOrderRepository.existsById(id)) return "The part order with that id does not exist";
        partOrderRepository.deleteById(id);
        return "Part order deleted successfully";
    }
}