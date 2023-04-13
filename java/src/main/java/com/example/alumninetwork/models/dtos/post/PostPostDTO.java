package com.example.alumninetwork.models.dtos.post;

import lombok.Data;

import java.util.Set;

@Data
public class PostPostDTO {
    private String title;
    private String body;
    private Integer replyParent;
    private Integer targetUser;
    private Integer targetGang;
    private Set<Integer> targetTopics;
}
