package com.sogeti.zoogeti.feedingapp.persistence.repository;

import com.sogeti.zoogeti.feedingapp.persistence.entity.Caretaker;
import com.sogeti.zoogeti.feedingapp.persistence.entity.FeedingMoment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedingMomentRepository extends JpaRepository<FeedingMoment, Long> {
    List<FeedingMoment> findAllByAssignedCaretaker(Caretaker caretaker);
}
