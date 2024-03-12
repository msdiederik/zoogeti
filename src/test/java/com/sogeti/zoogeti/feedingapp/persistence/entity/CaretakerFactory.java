package com.sogeti.zoogeti.feedingapp.persistence.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CaretakerFactory {
    public static Caretaker.CaretakerBuilder aCaretaker() {
        return Caretaker.builder()
                .id(1L)
                .name("Maarten");
    }
}
