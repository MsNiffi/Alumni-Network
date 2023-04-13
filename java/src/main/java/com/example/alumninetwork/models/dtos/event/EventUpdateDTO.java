package com.example.alumninetwork.models.dtos.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventUpdateDTO {
    private int eventId;
    private String eventName;
    private String eventDescription;
    private LocalDateTime timeUpdated;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean allowGuests;
}
