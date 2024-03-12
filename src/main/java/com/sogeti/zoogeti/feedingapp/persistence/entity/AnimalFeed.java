package com.sogeti.zoogeti.feedingapp.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AnimalFeed {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String brandName;
    @Enumerated(EnumType.ORDINAL)
    private FeedType feedType;
    private int amountOfKilogramsLeft;
}
