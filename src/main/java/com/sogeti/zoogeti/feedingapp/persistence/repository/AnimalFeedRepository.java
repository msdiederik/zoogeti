package com.sogeti.zoogeti.feedingapp.persistence.repository;

import com.sogeti.zoogeti.feedingapp.persistence.entity.AnimalFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnimalFeedRepository extends JpaRepository<AnimalFeed, Long> {
    Optional<AnimalFeed> findByBrandName(String brandName);
}
