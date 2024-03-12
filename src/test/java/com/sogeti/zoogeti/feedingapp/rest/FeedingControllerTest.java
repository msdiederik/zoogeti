package com.sogeti.zoogeti.feedingapp.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sogeti.zoogeti.feedingapp.exception.FoodNotAllowedException;
import com.sogeti.zoogeti.feedingapp.persistence.entity.AnimalFeedFactory;
import com.sogeti.zoogeti.feedingapp.persistence.entity.CaretakerFactory;
import com.sogeti.zoogeti.feedingapp.persistence.entity.FeedingMoment;
import com.sogeti.zoogeti.feedingapp.persistence.entity.FeedingMomentFactory;
import com.sogeti.zoogeti.feedingapp.persistence.entity.SpeciesFactory;
import com.sogeti.zoogeti.feedingapp.rest.request.ScheduleFeedingMomentRequest;
import com.sogeti.zoogeti.feedingapp.service.FeedingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedingController.class)
public class FeedingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    FeedingService feedingService;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void givenAValidRequest_whenSchedulingAFeedMoment_thenAFeedingMomentShouldBeReturned() throws Exception {
        // given
        ScheduleFeedingMomentRequest request = ScheduleFeedingMomentRequest.builder()
                .caretakerName("Willem")
                .speciesName("Hippos")
                .feedingBrand("Whatever poor animal being sacrificed to hippos")
                .desiredMoment(null)
                .build();

        FeedingMoment expectedFeedingMoment = FeedingMomentFactory.aFeedingMoment()
                .animalFeed(AnimalFeedFactory.aMeatFood().brandName(request.feedingBrand()).build())
                .assignedCaretaker(CaretakerFactory.aCaretaker().name(request.caretakerName()).build())
                .species(SpeciesFactory.aCarnivore().name(request.speciesName()).build())
                .feedDate(request.desiredMoment())
                .build();

        when(feedingService.scheduleFeedingMoment(
                request.speciesName(),
                request.feedingBrand(),
                request.caretakerName(),
                null))
                .thenReturn(expectedFeedingMoment);

        mockMvc.perform(post("/scheduleFeedingMoment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.assignedCaretaker.name").value(request.caretakerName()));
    }

    @Test
    void  givenFoodNotAllowedException_whenScheduleFeedingMoment_then400BadRequestGetsReturned() throws Exception {
        // given
        ScheduleFeedingMomentRequest request = ScheduleFeedingMomentRequest.builder()
                .caretakerName("Willem")
                .speciesName("Hippos")
                .feedingBrand("Whatever poor animal being sacrificed to hippos")
                .desiredMoment(LocalDateTime.now())
                .build();

        FoodNotAllowedException expectedException = new FoodNotAllowedException(request.feedingBrand(), request.speciesName());

        when(feedingService.scheduleFeedingMoment(
                eq(request.speciesName()),
                eq(request.feedingBrand()),
                eq(request.caretakerName()),
                any(LocalDateTime.class)))
                .thenThrow(expectedException);

        mockMvc.perform(post("/scheduleFeedingMoment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.detail").value(expectedException.getMessage()));
    }
}
