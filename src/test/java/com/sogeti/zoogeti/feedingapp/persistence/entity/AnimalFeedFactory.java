package com.sogeti.zoogeti.feedingapp.persistence.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnimalFeedFactory {
    public static AnimalFeed.AnimalFeedBuilder aMeatFood() {
        return AnimalFeed.builder()
                .id(1L)
                .brandName("Fresh Zebra")
                .amountOfKilogramsLeft(12)
                .feedType(FeedType.MEAT_ONLY);
    }

    public static AnimalFeed.AnimalFeedBuilder aVegetableFood() {
        return AnimalFeed.builder()
                .id(1L)
                .brandName("Mixed Vegetables")
                .amountOfKilogramsLeft(5)
                .feedType(FeedType.VEGETARIAN);
    }
}
