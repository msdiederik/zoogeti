package com.sogeti.zoogeti.feedingapp.service;

import com.sogeti.zoogeti.feedingapp.exception.CaretakerAlreadyBookedException;
import com.sogeti.zoogeti.feedingapp.exception.EntityNotFoundException;
import com.sogeti.zoogeti.feedingapp.exception.FoodNotAllowedException;
import com.sogeti.zoogeti.feedingapp.exception.NotEnoughFoodLeftException;
import com.sogeti.zoogeti.feedingapp.persistence.entity.AnimalFeed;
import com.sogeti.zoogeti.feedingapp.persistence.entity.Caretaker;
import com.sogeti.zoogeti.feedingapp.persistence.entity.FeedType;
import com.sogeti.zoogeti.feedingapp.persistence.entity.FeedingMoment;
import com.sogeti.zoogeti.feedingapp.persistence.entity.Species;
import com.sogeti.zoogeti.feedingapp.persistence.repository.AnimalFeedRepository;
import com.sogeti.zoogeti.feedingapp.persistence.repository.SpeciesRepository;
import com.sogeti.zoogeti.feedingapp.persistence.repository.FeedingMomentRepository;
import com.sogeti.zoogeti.feedingapp.persistence.repository.CaretakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedingService {
    private final FeedingStoragePort feedingStoragePort;

    public FeedingMoment scheduleFeedingMoment(String speciesName, String feedBrand, String caretakerName, LocalDateTime desiredMoment) {
            AnimalFeed animalFeed = feedingStoragePort.fetchAnimalFeed(feedBrand);
            Species species = feedingStoragePort.fetchSpecies(speciesName);
            Caretaker caretaker = feedingStoragePort.fetchCaretaker(caretakerName);
            List<FeedingMoment> plannedFeedingMomentsForCaretaker = feedingStoragePort.getFeedingMomentsByCaretaker(caretaker);

        if (foodIsNotAllowedForSpecies(species, animalFeed)) {
            throw new FoodNotAllowedException(feedBrand, speciesName);
        }

        if (animalFeed.getAmountOfKilogramsLeft() < species.getKilogramsPerFeedingMoment()) {
            throw new NotEnoughFoodLeftException(feedBrand, speciesName);
        }

        if (conflictingFeedingMoments(desiredMoment, plannedFeedingMomentsForCaretaker)) {
            throw new CaretakerAlreadyBookedException();
        }

        return feedingStoragePort.planFeedingMoment(species, caretaker, animalFeed, desiredMoment);
    }

    private boolean foodIsNotAllowedForSpecies(Species species, AnimalFeed animalFeed) {
        for (AnimalFeed allowedFood : species.getAllowedFeedingProducts()) {
            if(allowedFood.equals(animalFeed)) {
                return false;
            }
        }
        return true;
    }

    private boolean conflictingFeedingMoments(LocalDateTime desiredFeedingMoment, List<FeedingMoment> existingFeedingMoments) {
        for (FeedingMoment existingFeedingMoment : existingFeedingMoments) {
            if (ChronoUnit.MINUTES.between(desiredFeedingMoment, existingFeedingMoment.getFeedDate()) > 30) {
                return true;
            }
        }
        return false;
    }
}
