package com.example.alumninetwork.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TopicNotFoundException extends RuntimeException {
    /**
     * Thrown if a topic with a given ID does not exist
     *
     * @param id topic ID
     */
    public TopicNotFoundException(int id) {
        super("Topic with ID " + id + " does not exist");
    }
}
