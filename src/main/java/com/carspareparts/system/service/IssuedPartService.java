package com.carspareparts.system.service;

import com.carspareparts.system.domain.IssuedPart;
import com.carspareparts.system.domain.PartOrderStatus;
import com.carspareparts.system.repository.IssuedPartRepository;
import com.carspareparts.system.repository.PartOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class IssuedPartService {
    @Autowired private IssuedPartRepository issuedPartRepository;
    @Autowired private PartOrderRepository partOrderRepository;

    // E1: save — partOrder must exist and be FULFILLED; service sets issuedDate to today
    public Object save(UUID partOrderId, IssuedPart entity) {
        var partOrderOpt = partOrderRepository.findById(partOrderId);
        if (partOrderOpt.isEmpty()) {
            return new Result(404, "The part order with that id does not exist");
        }
        var partOrder = partOrderOpt.get();
        if (partOrder.getStatus() == null || partOrder.getStatus() != PartOrderStatus.FULFILLED) {
            return new Result(400, "Issued parts are only for FULFILLED part orders");
        }
        entity.setPartOrder(partOrder);
        entity.setIssuedDate(LocalDate.now());
        issuedPartRepository.save(entity);
        return new Result(201, "Issued part saved successfully");
    }

    public List<IssuedPart> getAll() { return issuedPartRepository.findAll(); }

    public Object getById(UUID id) {
        return issuedPartRepository.findById(id)
                .map(ip -> (Object) ip)
                .orElse("The issued part with that id does not exist");
    }

    // E2: issued part history for a customer by customerCode, newest first
    public List<IssuedPart> getByCustomerCode(String customerCode) {
        return issuedPartRepository.findByCustomerCode(customerCode);
    }

    // E3: delete an issued part
    public String delete(UUID id) {
        if (!issuedPartRepository.existsById(id)) return "The issued part with that id does not exist";
        issuedPartRepository.deleteById(id);
        return "Issued part deleted successfully";
    }

    // Simple inner record to carry HTTP status + message cleanly
    public record Result(int status, String message) {}
}
