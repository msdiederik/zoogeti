package com.sogeti.zoogeti.feedingapp.rest;

import com.sogeti.zoogeti.feedingapp.persistence.entity.AnimalFeed;
import com.sogeti.zoogeti.feedingapp.persistence.entity.FeedingMoment;
import com.sogeti.zoogeti.feedingapp.persistence.repository.AnimalFeedRepository;
import com.sogeti.zoogeti.feedingapp.rest.request.ScheduleFeedingMomentRequest;
import com.sogeti.zoogeti.feedingapp.service.FeedingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedingController {
    private final FeedingService feedingService;
    @PostMapping("/scheduleFeedingMoment")
    public FeedingMoment scheduleFeedingMoment(@RequestBody ScheduleFeedingMomentRequest scheduleFeedingMomentRequest) {
        FeedingMoment result = feedingService.scheduleFeedingMoment(
                scheduleFeedingMomentRequest.speciesName(),
                scheduleFeedingMomentRequest.feedingBrand(),
                scheduleFeedingMomentRequest.caretakerName(),
                scheduleFeedingMomentRequest.desiredMoment());
        return result;
    }
}
