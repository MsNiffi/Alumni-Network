package com.example.alumninetwork.models.dtos.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    private int eventId;
    private String eventName;
    private String eventDescription;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean allowGuests;
    private Integer gangId;
    private String creatorId;
}
