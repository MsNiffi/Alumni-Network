package com.example.alumninetwork.models.dtos.person;

import lombok.Data;

import java.util.Set;

@Data
public class PersonDTO {
    private String personId;
    private String firstName;
    private String lastName;
    private String userName;
    private String funFact;
    private String workStatus;
    private String pictureUrl;
    private String bio;
    private Set<Integer> topics;
    private Set<Integer> gangs;
    private Set<Integer> events;

}
