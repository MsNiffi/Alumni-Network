package com.example.alumninetwork.models.dtos.gang;

import lombok.Data;

import java.util.Set;

@Data
public class GangPostDTO {
    private String gangName;
    private Boolean isPrivate;
    private String gangDescription;
    private Set<String> persons;
}
