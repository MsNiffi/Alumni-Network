package com.example.alumninetwork.models.dtos.event;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class EventPostDTO {
    private String eventName;
    private String eventDescription;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean allowGuests;
    private Set<Integer> gangs;
    private Set<Integer> eventTopics;
    private Set<String> persons;
}
