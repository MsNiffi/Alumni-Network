package com.example.alumninetwork.models.dtos.post;

import lombok.Data;
import net.minidev.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostDTO {
    private int postId;
    private String title;
    private String body;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
    private JSONObject sender;
    private Integer replyParent;
    private Integer targetGang;
    private Integer targetEvent;
    private Set<JSONObject> targetTopics;
    private Set<Integer> replies;
}
