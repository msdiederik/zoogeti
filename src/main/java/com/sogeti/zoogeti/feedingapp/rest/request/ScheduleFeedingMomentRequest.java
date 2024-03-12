package com.sogeti.zoogeti.feedingapp.rest.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScheduleFeedingMomentRequest (
        String speciesName,
        String feedingBrand,
        String caretakerName,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
        LocalDateTime desiredMoment
) {}
