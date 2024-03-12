package com.sogeti.zoogeti.feedingapp.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Species {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int kilogramsPerFeedingMoment;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "species_feed",
            joinColumns = @JoinColumn(name = "species_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "feed_id", referencedColumnName = "id"))
    private List<AnimalFeed> allowedFeedingProducts;
    @Enumerated(EnumType.ORDINAL)
    private Diet diet;
}
