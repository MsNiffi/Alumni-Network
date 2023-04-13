package com.example.alumninetwork.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {
    /**
     * Thrown if a user with a given ID does not exist
     *
     * @param id user ID
     */
    public PersonNotFoundException(int id) {
        super("User with ID " + id + " does not exist");
    }

    public PersonNotFoundException(String id) {
        super("User with ID " + id + " does not exist");
    }
}
