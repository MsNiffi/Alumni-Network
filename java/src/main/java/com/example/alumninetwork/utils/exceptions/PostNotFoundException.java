package com.example.alumninetwork.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException {
    /**
     * Thrown if a post with a given ID does not exist
     *
     * @param id post ID
     */
    public PostNotFoundException(int id) {
        super("Post with ID " + id + " does not exist");
    }
}
