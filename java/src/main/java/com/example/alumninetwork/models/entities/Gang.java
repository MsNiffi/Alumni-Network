package com.example.alumninetwork.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "gang")
public class Gang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gangId;
    @Column(length = 40, nullable = false, unique = true)
    private String gangName;
    @Column(nullable = false)
    private Boolean isPrivate;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String gangDescription;
    @ManyToMany(mappedBy = "gangs")
    private Set<Person> persons;
    @OneToMany(mappedBy = "targetGang")
    private Set<Post> posts;
    @ManyToMany(mappedBy = "gangs")
    private Set<Event> events;
}
