package com.example.alumninetwork.models.dtos.post;
import lombok.Data;
import net.minidev.json.JSONObject;

import java.time.LocalDateTime;

@Data
public class PostDmDTO {
    private String title;
    private String body;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
    private JSONObject sender;
}
