package com.carspareparts.system.repository;

import com.carspareparts.system.domain.StoreSection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StoreSectionRepository extends JpaRepository<StoreSection, UUID> {}