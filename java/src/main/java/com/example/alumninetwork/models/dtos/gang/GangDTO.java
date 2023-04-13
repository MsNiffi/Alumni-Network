package com.example.alumninetwork.models.dtos.gang;

import lombok.Data;

import java.util.Set;

@Data
public class GangDTO {
    private int gangId;
    private String gangName;
    private Boolean isPrivate;
    private String gangDescription;
    private String creatorId;
    private Set<String> persons;
    private Set<Integer> posts;
}
