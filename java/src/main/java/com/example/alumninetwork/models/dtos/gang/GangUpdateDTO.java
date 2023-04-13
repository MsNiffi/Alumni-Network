package com.example.alumninetwork.models.dtos.gang;

import lombok.Data;

@Data
public class GangUpdateDTO {
    private int gangId;
    private String gangName;
    private Boolean isPrivate;
    private String gangDescription;
}
