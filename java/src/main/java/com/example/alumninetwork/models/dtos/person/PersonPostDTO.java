package com.example.alumninetwork.models.dtos.person;

import lombok.Data;

@Data
public class PersonPostDTO {
    private String personId;
    private String firstName;
    private String lastName;
    private String userName;
    private String funFact;
    private String bio;
    private String workStatus;
    private String pictureUrl;
}
