package com.example.alumninetwork.mappers;

import com.example.alumninetwork.models.dtos.post.PostDTO;
import com.example.alumninetwork.models.dtos.post.PostDmDTO;
import com.example.alumninetwork.models.dtos.post.PostPostDTO;
import com.example.alumninetwork.models.entities.*;
import com.example.alumninetwork.services.event.EventService;
import com.example.alumninetwork.services.gang.GangService;
import com.example.alumninetwork.services.post.PostService;
import com.example.alumninetwork.services.topic.TopicService;
import net.minidev.json.JSONObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

// Mapper for posts

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private PostService postService;
    @Autowired
    private GangService gangService;
    @Autowired
    private EventService eventService;
    @Autowired
    private TopicService topicService;

    // Mappings from post to DTO
    @Mapping(source = "targetGang", target = "targetGang", qualifiedByName = "targetGangToId")
    @Mapping(source = "targetEvent", target = "targetEvent", qualifiedByName = "targetEventToId")
    @Mapping(source = "targetTopics", target = "targetTopics", qualifiedByName = "topicsToJson")
    @Mapping(source = "replies", target = "replies", qualifiedByName = "repliesToIds")
    @Mapping(source = "replyParent", target = "replyParent", qualifiedByName = "replyParentToId")
    public abstract PostDTO postToPostDto(Post post);

    public abstract Collection<PostDTO> postToPostDto(Collection<Post> posts);

    @Mapping(target = "targetGang", source = "targetGang", qualifiedByName = "gangIdToTargetGang")
    @Mapping(target = "targetTopics", source = "targetTopics", qualifiedByName = "topicIdsToTopic")
    @Mapping(target = "replyParent", source = "replyParent", qualifiedByName = "replyParentIdToPost")
    public abstract Post postPostDtoToPost(PostPostDTO postDTO);

    public abstract Collection<PostDmDTO> postDmToDto(Collection<Post> posts);

    @Named("topicsToJson")
    Set<JSONObject> topicsToJson(Set<Topic> topics) {
        if (topics == null) {
            return null;
        }
        Set<JSONObject> topicSet = new HashSet<>();
        topics.forEach(topic -> {
            JSONObject obj = new JSONObject();
            obj.put("id", topic.getTopicId());
            obj.put("name", topic.getTopicName());
            obj.put("description", topic.getTopicDescription());
            topicSet.add(obj);
        });
        return topicSet;
    }

    // Mapping to get a json object of the author/sender of the post
    JSONObject map(Person person) {
        if (person == null) {
            return null;
        }
        JSONObject obj = new JSONObject();
        obj.put("id", person.getPersonId());
        obj.put("name", person.getFirstName() + " " + person.getLastName());
        obj.put("pictureUrl", person.getPictureUrl());
        return obj;
    }

    @Named("targetGangToId")
    Integer targetGangToId(Gang gang) {
        if (gang == null) {
            return null;
        }
        return gang.getGangId();
    }

    @Named("targetEventToId")
    Integer targetEventToId(Event event) {
        if (event == null) {
            return null;
        }
        return event.getEventId();
    }

    @Named("gangIdToTargetGang")
    Gang gangIdToTargetGang(Integer gangId) {
        if (gangId == null) {
            return null;
        }
        return gangService.findById(gangId);
    }

    @Named("eventIdToTargetGang")
    Event eventIdToTargetGang(Integer eventId) {
        return eventService.findById(eventId);
    }

    @Named("topicIdsToTopic")
    Set<Topic> topicIdsToTopic(Set<Integer> ids) {
        return ids.stream().map(id -> topicService.findById(id)).collect(Collectors.toSet());
    }

    @Named("replyParentToId")
    Integer replyParentToId(Post post) {
        if (post == null) {
            return null;
        }
        return post.getPostId();
    }

    @Named("repliesToIds")
    Set<Integer> repliesToIds(Set<Post> replies) {
        if (replies == null) {
            return null;
        }
        return replies.stream().map(Post::getPostId).collect(Collectors.toSet());
    }

    @Named("replyParentIdToPost")
    Post replyParentIdToPost(Integer replyParentId) {
        if (replyParentId == null) {
            return null;
        }
        return postService.findById(replyParentId);
    }
}
