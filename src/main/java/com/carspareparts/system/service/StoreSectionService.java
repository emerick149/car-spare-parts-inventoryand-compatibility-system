package com.carspareparts.system.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carspareparts.system.domain.StoreSection;
import com.carspareparts.system.repository.StoreSectionRepository;

@Service
public class StoreSectionService {
    @Autowired private StoreSectionRepository storeSectionRepository;

    // A1 (CRUD): create store section
    public String save(StoreSection entity) {
        storeSectionRepository.save(entity);
        return "Store section saved successfully";
    }

    // A1 (CRUD): get all store sections
    public List<StoreSection> getAll() { return storeSectionRepository.findAll(); }

    // A1 (CRUD): get store section by id
    public Object getById(UUID id) {
        return storeSectionRepository.findById(id)
                .map(storeSection -> (Object) storeSection)
                .orElse("The store section with that id does not exist");
    }

    // A1 (CRUD): update store section
    public String update(UUID id, StoreSection newData) {
        return storeSectionRepository.findById(id).map(existing -> {
            existing.setSectionNumber(newData.getSectionNumber());
            existing.setName(newData.getName());
            storeSectionRepository.save(existing);
            return "Store section updated successfully";
        }).orElse("The store section with that id does not exist");
    }

    // C2: get store sections not occupied by any storekeeper, sorted by sectionNumber
    public List<StoreSection> getFreeSections() {
        return storeSectionRepository.findFreeSections();
    }

    // A1 (CRUD): delete store section
    public String delete(UUID id) {
        if (!storeSectionRepository.existsById(id)) return "The store section with that id does not exist";
        storeSectionRepository.deleteById(id);
        return "Store section deleted successfully";
    }
}
