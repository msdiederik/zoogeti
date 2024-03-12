package com.sogeti.zoogeti.feedingapp.service;

import com.sogeti.zoogeti.feedingapp.persistence.entity.AnimalFeed;
import com.sogeti.zoogeti.feedingapp.persistence.entity.AnimalFeedFactory;
import com.sogeti.zoogeti.feedingapp.persistence.entity.Caretaker;
import com.sogeti.zoogeti.feedingapp.persistence.entity.CaretakerFactory;
import com.sogeti.zoogeti.feedingapp.persistence.entity.FeedingMoment;
import com.sogeti.zoogeti.feedingapp.persistence.entity.Species;
import com.sogeti.zoogeti.feedingapp.persistence.entity.SpeciesFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedingServiceTest {
    @Mock
    FeedingStoragePort feedingStoragePort;
    @InjectMocks
    FeedingService feedingService;

    @Test
    void givenNoConflicts_whenSchedulingFeedingMoment_thenANewFeedingMomentIsCreated() {
        //given
        AnimalFeed expectedAnimalFeed = AnimalFeedFactory.aMeatFood().build();
        Species expectedSpecies = SpeciesFactory
                .aCarnivore()
                .build();
        Caretaker expectedCaretaker = CaretakerFactory.aCaretaker().build();
        LocalDateTime expectedDateTime = LocalDateTime.now();
        FeedingMoment expectedFeedingMoment = FeedingMoment.builder()
                        .id(1L).feedDate(expectedDateTime).animalFeed(expectedAnimalFeed)
                        .species(expectedSpecies).assignedCaretaker(expectedCaretaker).build();

        when(feedingStoragePort.fetchAnimalFeed(expectedAnimalFeed.getBrandName()))
                .thenReturn(expectedAnimalFeed);

        when(feedingStoragePort.fetchSpecies(expectedSpecies.getName()))
                .thenReturn(expectedSpecies);

        when(feedingStoragePort.fetchCaretaker(expectedCaretaker.getName()))
                .thenReturn(expectedCaretaker);

        when(feedingStoragePort.getFeedingMomentsByCaretaker(expectedCaretaker))
                .thenReturn(Collections.emptyList());

        when(feedingStoragePort.planFeedingMoment(expectedSpecies, expectedCaretaker, expectedAnimalFeed, expectedDateTime))
                .thenReturn(expectedFeedingMoment);

        // when
        FeedingMoment result = feedingService.scheduleFeedingMoment(expectedSpecies.getName(),
                expectedAnimalFeed.getBrandName(), expectedCaretaker.getName(), expectedDateTime);

        assertEquals(expectedFeedingMoment, result);
        verify(feedingStoragePort).planFeedingMoment(expectedSpecies, expectedCaretaker, expectedAnimalFeed, expectedDateTime);
        verifyNoMoreInteractions(feedingStoragePort);
    }

}
