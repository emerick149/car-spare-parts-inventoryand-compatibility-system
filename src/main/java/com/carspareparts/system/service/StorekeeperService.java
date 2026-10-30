package com.carspareparts.system.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carspareparts.system.domain.StoreSection;
import com.carspareparts.system.domain.Storekeeper;
import com.carspareparts.system.repository.StoreSectionRepository;
import com.carspareparts.system.repository.StorekeeperRepository;
import com.carspareparts.system.repository.VehicleMakeRepository;

@Service
public class StorekeeperService {
    @Autowired private StorekeeperRepository storekeeperRepository;
    @Autowired private VehicleMakeRepository vehicleMakeRepository;
    @Autowired private StoreSectionRepository storeSectionRepository;

    // A1 (CRUD): create storekeeper
    public String save(Storekeeper entity) {
        storekeeperRepository.save(entity);
        return "Storekeeper saved successfully";
    }

    // A1 (CRUD): get all storekeepers
    public List<Storekeeper> getAll() { return storekeeperRepository.findAll(); }

    // A1 (CRUD): get storekeeper by id
    public Object getById(UUID id) {
        return storekeeperRepository.findById(id)
                .map(storekeeper -> (Object) storekeeper)
                .orElse("The storekeeper with that id does not exist");
    }

    // A1 (CRUD): update storekeeper
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

    // C1: get storekeepers by vehicle make name (case-insensitive)
    public List<Storekeeper> getByVehicleMake(String name) {
        return storekeeperRepository.findByVehicleMakeName(name);
    }

    // D3: add a single vehicle make to a storekeeper (no duplicates allowed)
    public Map.Entry<Integer, String> addVehicleMake(UUID storekeeperID, UUID vehicleMakeId) {
        var storekeeperOpt = storekeeperRepository.findById(storekeeperID);
        if (storekeeperOpt.isEmpty()) {
            return Map.entry(404, "The storekeeper with that id does not exist");
        }
        var vehicleMakeOpt = vehicleMakeRepository.findById(vehicleMakeId);
        if (vehicleMakeOpt.isEmpty()) {
            return Map.entry(404, "That vehicle make does not exist");
        }
        Storekeeper storekeeper = storekeeperOpt.get();
        boolean alreadyHas = storekeeper.getVehicleMakes().stream()
                .anyMatch(vm -> vm.getId().equals(vehicleMakeId));
        if (alreadyHas) {
            return Map.entry(409, "The storekeeper already has that vehicle make");
        }
        storekeeper.getVehicleMakes().add(vehicleMakeOpt.get());
        storekeeperRepository.save(storekeeper);
        return Map.entry(200, "Vehicle make added to the storekeeper");
    }

    // D4: move a storekeeper to a different store section by sectionNumber (section must be free)
    public Map.Entry<Integer, String> changeStoreSection(UUID storekeeperID, int sectionNumber) {
        var storekeeperOpt = storekeeperRepository.findById(storekeeperID);
        if (storekeeperOpt.isEmpty()) {
            return Map.entry(404, "The storekeeper with that id does not exist");
        }
        var sectionOpt = storeSectionRepository.findBySectionNumber(sectionNumber);
        if (sectionOpt.isEmpty()) {
            return Map.entry(404, "The store section with that sectionNumber does not exist");
        }
        StoreSection targetSection = sectionOpt.get();
        boolean taken = storekeeperRepository.existsByStoreSection_SectionNumber(sectionNumber);
        if (taken) {
            return Map.entry(409, "That store section is taken by another storekeeper");
        }
        Storekeeper storekeeper = storekeeperOpt.get();
        storekeeper.setStoreSection(targetSection);
        storekeeperRepository.save(storekeeper);
        return Map.entry(200, "Store section changed successfully");
    }

    // A1 (CRUD): delete storekeeper
    public String delete(UUID id) {
        if (!storekeeperRepository.existsById(id)) return "The storekeeper with that id does not exist";
        storekeeperRepository.deleteById(id);
        return "Storekeeper deleted successfully";
    }
}
