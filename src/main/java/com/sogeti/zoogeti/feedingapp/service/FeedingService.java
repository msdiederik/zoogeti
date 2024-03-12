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
    private final AnimalFeedRepository animalFeedRepository;
    private final SpeciesRepository speciesRepository;
    private final FeedingMomentRepository feedingMomentRepository;
    private final CaretakerRepository caretakerRepository;

    public FeedingMoment scheduleFeedingMoment(String speciesName, String feedBrand, String caretakerName, LocalDateTime desiredMoment) {
        AnimalFeed animalFeed = animalFeedRepository.findByBrandName(feedBrand).orElseThrow(() -> new EntityNotFoundException("AnimalFeed", "brandName", feedBrand));
        Species species = speciesRepository.findByName(speciesName).orElseThrow(() -> new EntityNotFoundException("Species", "name", speciesName));
        Caretaker caretaker = caretakerRepository.findByName(caretakerName).orElseThrow(() -> new EntityNotFoundException("Caretaker", "name", caretakerName));
        List<FeedingMoment> plannedFeedingMomentsForCaretaker = feedingMomentRepository.findAllByAssignedCaretaker(caretaker);

        if (!species.getAllowedFeedingProducts().contains(animalFeed)) {
            throw new FoodNotAllowedException(feedBrand, speciesName);
        }

        if (animalFeed.getAmountOfKilogramsLeft() < species.getKilogramsPerFeedingMoment()) {
            throw new NotEnoughFoodLeftException(feedBrand, speciesName);
        }

        if (conflictingFeedingMoments(desiredMoment, plannedFeedingMomentsForCaretaker)) {
            throw new CaretakerAlreadyBookedException();
        }

        FeedingMoment feedingMoment = FeedingMoment.builder()
                .feedDate(desiredMoment)
                .animalFeed(animalFeed)
                .species(species)
                .assignedCaretaker(caretaker)
                .build();

        return feedingMomentRepository.save(feedingMoment);
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
