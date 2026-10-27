package com.carspareparts.system.repository;

import com.carspareparts.system.domain.Storekeeper;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StorekeeperRepository extends JpaRepository<Storekeeper, UUID> {}