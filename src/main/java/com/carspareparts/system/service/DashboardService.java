package com.carspareparts.system.service;

import com.carspareparts.system.domain.PartOrderStatus;
import com.carspareparts.system.repository.CustomerRepository;
import com.carspareparts.system.repository.IssuedPartRepository;
import com.carspareparts.system.repository.PartOrderRepository;
import com.carspareparts.system.repository.StoreSectionRepository;
import com.carspareparts.system.repository.StorekeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired private CustomerRepository customerRepository;
    @Autowired private StorekeeperRepository storekeeperRepository;
    @Autowired private StoreSectionRepository storeSectionRepository;
    @Autowired private PartOrderRepository partOrderRepository;
    @Autowired private IssuedPartRepository issuedPartRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("customers",         customerRepository.count());
        summary.put("storekeepers",      storekeeperRepository.count());
        summary.put("freeStoreSections", storeSectionRepository.countFreeSections());
        summary.put("ordersToday",       partOrderRepository.countByOrderDate(LocalDate.now()));
        summary.put("placed",            partOrderRepository.countByStatus(PartOrderStatus.PLACED));
        summary.put("issuedParts",       issuedPartRepository.count());
        return summary;
    }
}
