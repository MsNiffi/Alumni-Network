package com.example.alumninetwork.repositories;

import com.example.alumninetwork.models.entities.Gang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GangRepository extends JpaRepository<Gang, Integer> {
}
