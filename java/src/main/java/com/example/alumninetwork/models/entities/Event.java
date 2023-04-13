package com.example.alumninetwork.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;
    @Column(nullable = false, length = 50)
    private String eventName;
    @Column(nullable = false, length = 500)
    private String eventDescription;
    @Column(name = "time_created", nullable = false)
    private LocalDateTime timeCreated;
    @Column(name = "time_updated")
    private LocalDateTime timeUpdated;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;
    @Column(nullable = false)
    private Boolean allowGuests;
    @ManyToMany(mappedBy = "events")
    private Set<Person> persons;
    @ManyToOne
    private Person creatorId;
    @ManyToMany
    @JoinTable(name = "event_gang", joinColumns = {@JoinColumn(name = "event_id")}, inverseJoinColumns = {@JoinColumn(name = "gang_id")})
    private Set<Gang> gangs;
    @ManyToMany
    @JoinTable(name = "event_topic", joinColumns = {@JoinColumn(name = "event_id")}, inverseJoinColumns = {@JoinColumn(name = "topic_id")})
    private Set<Topic> eventTopics;
    @OneToMany(mappedBy = "targetEvent")
    private Set<Post> eventPosts;
    @ManyToMany
    @JoinTable(name = "event_user_invite", joinColumns = {@JoinColumn(name = "event_id")}, inverseJoinColumns = {@JoinColumn(name = "person_id")})
    private Set<Person> eventUserInvite;
}