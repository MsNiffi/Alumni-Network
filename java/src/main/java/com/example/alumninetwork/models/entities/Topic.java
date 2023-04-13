package com.example.alumninetwork.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int topicId;
    @Column(length = 40, unique = true, nullable = false)
    private String topicName;
    @Column(columnDefinition = "TEXT")
    private String topicDescription;
    @ManyToMany(mappedBy = "targetTopics")
    private Set<Post> posts;
    @ManyToMany(mappedBy = "topics")
    private Set<Person> persons;
    @ManyToMany(mappedBy = "eventTopics")
    private Set<Event> events;
}
