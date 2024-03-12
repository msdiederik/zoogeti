package com.sogeti.zoogeti.feedingapp.persistence.repository;

import com.sogeti.zoogeti.feedingapp.persistence.entity.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpeciesRepository extends JpaRepository<Species, Long> {
    Optional<Species> findByName(String name);
}
