package com.example.alumninetwork.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EventNotFoundException extends RuntimeException {
    /**
     * Thrown if an event with a given ID does not exist
     *
     * @param id event ID
     */
    public EventNotFoundException(int id) {
        super("Event with ID " + id + " does not exist");
    }
}
