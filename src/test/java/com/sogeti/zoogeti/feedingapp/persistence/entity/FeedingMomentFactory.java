package com.sogeti.zoogeti.feedingapp.persistence.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedingMomentFactory {
    public static FeedingMoment.FeedingMomentBuilder aFeedingMoment() {
        return FeedingMoment.builder()
                .id(1L)
                .assignedCaretaker(CaretakerFactory.aCaretaker().build())
                .animalFeed(AnimalFeedFactory.aMeatFood().build())
                .species(SpeciesFactory.aCarnivore().build())
                .feedDate(LocalDateTime.now());
    }
}
