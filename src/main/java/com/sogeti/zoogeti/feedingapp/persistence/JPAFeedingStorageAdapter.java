package com.sogeti.zoogeti.feedingapp.persistence;

import com.sogeti.zoogeti.feedingapp.exception.EntityNotFoundException;
import com.sogeti.zoogeti.feedingapp.persistence.entity.AnimalFeed;
import com.sogeti.zoogeti.feedingapp.persistence.entity.Caretaker;
import com.sogeti.zoogeti.feedingapp.persistence.entity.FeedingMoment;
import com.sogeti.zoogeti.feedingapp.persistence.entity.Species;
import com.sogeti.zoogeti.feedingapp.persistence.repository.CaretakerRepository;
import com.sogeti.zoogeti.feedingapp.service.FeedingStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JPAFeedingStorageAdapter implements FeedingStoragePort {
    private final CaretakerRepository caretakerRepository;

    @Override
    public Caretaker fetchCaretaker(String name) {
        return caretakerRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Caretaker", "Name", name));
    }

    @Override
    public Species fetchSpecies(String name) {
        return null;
    }

    @Override
    public AnimalFeed fetchAnimalFeed(String brandName) {
        return null;
    }

    @Override
    public List<FeedingMoment> getFeedingMomentsByCaretaker(Caretaker caretaker) {
        return null;
    }

    @Override
    public FeedingMoment planFeedingMoment(Species species, Caretaker caretaker, AnimalFeed animalFeed, LocalDateTime dateTime) {
        return null;
    }
}
