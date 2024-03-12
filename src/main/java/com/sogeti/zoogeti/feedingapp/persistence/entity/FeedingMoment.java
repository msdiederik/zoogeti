package com.sogeti.zoogeti.feedingapp.persistence.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FeedingMoment {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime feedDate;
    @ManyToOne
    private Caretaker assignedCaretaker;
    @ManyToOne
    private Species species;
    @ManyToOne
    private AnimalFeed animalFeed;
}
