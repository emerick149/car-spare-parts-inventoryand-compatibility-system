package com.carspareparts.system.service;

import com.carspareparts.system.domain.PartOrder;
import com.carspareparts.system.domain.PartOrderStatus;
import com.carspareparts.system.repository.PartOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PartOrderService {
    @Autowired private PartOrderRepository partOrderRepository;

    public String save(PartOrder entity) {
        partOrderRepository.save(entity);
        return "Part order saved successfully";
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