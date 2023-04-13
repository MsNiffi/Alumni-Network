package com.example.alumninetwork.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GangNotFoundException extends RuntimeException {
    /**
     * Thrown if a group with a given ID does not exist
     *
     * @param id group ID
     */
    public GangNotFoundException(int id) {
        super("Gang with ID " + id + " does not exist");
    }
}
