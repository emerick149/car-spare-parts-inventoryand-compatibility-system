package com.carspareparts.system.service;

import com.carspareparts.system.domain.Storekeeper;
import com.carspareparts.system.repository.StorekeeperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class StorekeeperService {
    @Autowired private StorekeeperRepository storekeeperRepository;

    public String save(Storekeeper entity) {
        storekeeperRepository.save(entity);
        return "Storekeeper saved successfully";
    }
    public List<Storekeeper> getAll() { return storekeeperRepository.findAll(); }

    public Object getById(UUID id) {
        return storekeeperRepository.findById(id)
                .map(storekeeper -> (Object) storekeeper)
                .orElse("The storekeeper with that id does not exist");
    }

    public String update(UUID id, Storekeeper newData) {
        return storekeeperRepository.findById(id).map(existing -> {
            existing.setFirstName(newData.getFirstName());
            existing.setLastName(newData.getLastName());
            existing.setPhone(newData.getPhone());
            existing.setStoreSection(newData.getStoreSection());
            existing.setVehicleMakes(newData.getVehicleMakes());
            storekeeperRepository.save(existing);
            return "Storekeeper updated successfully";
        }).orElse("The storekeeper with that id does not exist");
    }

    public String delete(UUID id) {
        if (!storekeeperRepository.existsById(id)) return "The storekeeper with that id does not exist";
        storekeeperRepository.deleteById(id);
        return "Storekeeper deleted successfully";
    }
}