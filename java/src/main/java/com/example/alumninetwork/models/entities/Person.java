package com.example.alumninetwork.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Person {
    @Id
    private String personId;
    @Column(length = 40, nullable = false)
    String firstName;
    @Column(length = 40, nullable = false)
    String lastName;
    @Column(length = 20, unique = true, nullable = false)
    String userName;
    @Column(length = 120)
    String funFact;
    @Column(length = 90, nullable = false)
    String workStatus;
    String pictureUrl;
    @Column(nullable = false)
    String bio;
    @ManyToMany
    @JoinTable(name = "person_gang", joinColumns = {@JoinColumn(name = "person_id")}, inverseJoinColumns = {@JoinColumn(name = "gang_id")})
    private Set<Gang> gangs;
    @ManyToMany
    @JoinTable(name = "person_event", joinColumns = {@JoinColumn(name = "person_id")}, inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private Set<Event> events;
    @OneToMany(mappedBy = "creatorId")
    private Set<Event> ownedEvents;
    @ManyToMany
    @JoinTable(name = "person_topic", joinColumns = {@JoinColumn(name = "person_id")}, inverseJoinColumns = {@JoinColumn(name = "topic_id")})
    private Set<Topic> topics;
    @ManyToMany(mappedBy = "eventUserInvite")
    private Set<Event> eventUserInvite;
}
