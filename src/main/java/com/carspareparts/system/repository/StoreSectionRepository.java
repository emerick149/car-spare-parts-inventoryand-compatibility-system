package com.carspareparts.system.repository;

import com.carspareparts.system.domain.StoreSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreSectionRepository extends JpaRepository<StoreSection, UUID> {

    // C2: store sections not occupied by any storekeeper, sorted by sectionNumber
    @Query("SELECT ss FROM StoreSection ss WHERE NOT EXISTS (SELECT sk FROM Storekeeper sk WHERE sk.storeSection = ss) ORDER BY ss.sectionNumber ASC")
    List<StoreSection> findFreeSections();

    // D4: find a store section by sectionNumber
    Optional<StoreSection> findBySectionNumber(int sectionNumber);
}