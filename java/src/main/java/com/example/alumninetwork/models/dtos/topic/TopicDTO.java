package com.example.alumninetwork.models.dtos.topic;

import lombok.Data;

import java.util.Set;

@Data
public class TopicDTO {
    private int topicId;
    private String topicName;
    private String topicDescription;
    private Set<Integer> posts;

}
