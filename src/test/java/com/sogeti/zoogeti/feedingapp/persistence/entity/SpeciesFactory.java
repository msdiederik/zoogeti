package com.sogeti.zoogeti.feedingapp.persistence.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpeciesFactory {
    public static Species.SpeciesBuilder aCarnivore() {
        return Species.builder()
                .id(1L)
                .name("Lion")
                .kilogramsPerFeedingMoment(6)
                .allowedFeedingProducts(List.of(AnimalFeedFactory.aMeatFood().build()))
                .diet(Diet.CARNIVORE);
    }

    public static Species.SpeciesBuilder aHerbivore() {
        return Species.builder()
                .id(2L)
                .name("Giraffe")
                .kilogramsPerFeedingMoment(3)
                .allowedFeedingProducts(List.of(AnimalFeedFactory.aVegetableFood().build()))
                .diet(Diet.HERBIVORE);
    }
}
