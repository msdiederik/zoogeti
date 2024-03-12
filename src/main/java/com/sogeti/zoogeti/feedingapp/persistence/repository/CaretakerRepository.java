package com.sogeti.zoogeti.feedingapp.persistence.repository;

import com.sogeti.zoogeti.feedingapp.persistence.entity.Caretaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaretakerRepository extends JpaRepository<Caretaker, Long> {
    Optional<Caretaker> findByName(String name);
}
