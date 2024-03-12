package com.sogeti.zoogeti.feedingapp.service;

import com.sogeti.zoogeti.feedingapp.persistence.entity.AnimalFeed;
import com.sogeti.zoogeti.feedingapp.persistence.entity.Caretaker;
import com.sogeti.zoogeti.feedingapp.persistence.entity.FeedingMoment;
import com.sogeti.zoogeti.feedingapp.persistence.entity.Species;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedingStoragePort {
    Caretaker fetchCaretaker(String name);
    Species fetchSpecies(String name);
    AnimalFeed fetchAnimalFeed(String brandName);
    List<FeedingMoment> getFeedingMomentsByCaretaker(Caretaker caretaker);
    FeedingMoment planFeedingMoment(Species species, Caretaker caretaker, AnimalFeed animalFeed, LocalDateTime dateTime);
}
