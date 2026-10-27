package com.carspareparts.system.service;

import com.carspareparts.system.domain.StoreSection;
import com.carspareparts.system.repository.StoreSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class StoreSectionService {
    @Autowired private StoreSectionRepository storeSectionRepository;

    public String save(StoreSection entity) {
        storeSectionRepository.save(entity);
        return "Store section saved successfully";
    }
    public List<StoreSection> getAll() { return storeSectionRepository.findAll(); }

    public Object getById(UUID id) {
        return storeSectionRepository.findById(id)
                .map(storeSection -> (Object) storeSection)
                .orElse("The store section with that id does not exist");
    }

    public String update(UUID id, StoreSection newData) {
        return storeSectionRepository.findById(id).map(existing -> {
            existing.setSectionNumber(newData.getSectionNumber());
            existing.setName(newData.getName());
            storeSectionRepository.save(existing);
            return "Store section updated successfully";
        }).orElse("The store section with that id does not exist");
    }

    public String delete(UUID id) {
        if (!storeSectionRepository.existsById(id)) return "The store section with that id does not exist";
        storeSectionRepository.deleteById(id);
        return "Store section deleted successfully";
    }
}