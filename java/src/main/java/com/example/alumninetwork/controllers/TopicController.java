package com.example.alumninetwork.controllers;

import com.example.alumninetwork.mappers.TopicMapper;
import com.example.alumninetwork.models.dtos.topic.TopicDTO;
import com.example.alumninetwork.models.dtos.topic.TopicPostDTO;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.models.entities.Topic;
import com.example.alumninetwork.services.person.PersonService;
import com.example.alumninetwork.services.topic.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/topics")
@Tag(name = "Topics", description = "Endpoints to interact with topics")
public class TopicController {

    private final TopicMapper topicMapper;
    private final TopicService topicService;
    private final PersonService personService;

    public TopicController(TopicMapper topicMapper, TopicService topicService, PersonService personService) {
        this.topicMapper = topicMapper;
        this.topicService = topicService;
        this.personService = personService;
    }

    @Operation(summary = "Return all topics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TopicDTO.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "No topics found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(topicMapper.topicToTopicDto(topicService.findAll()));
    }

    @Operation(summary = "Return topic by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TopicDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Topic not found", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(topicMapper.topicToTopicDto(topicService.findById(id)));
    }

    @Operation(summary = "Create a new topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New topic created and creator subscribed to it",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TopicDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Client action error", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createTopic(@RequestBody TopicPostDTO topicPostDTO, Principal principal) {
        if (topicPostDTO.getTopicName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title cannot be null");
        }
        Person creator = personService.findById(principal.getName());
        Topic topic = topicMapper.topicPostDtoToTopic(topicPostDTO);
        // Adding the topics to the person
        creator.getTopics().add(topic);
        topicService.add(topic);
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        URI location = URI.create(baseUrl + "/api/v1/topic/" + topic.getTopicId());
        return ResponseEntity.created(location).body(topic.getTopicId());
    }

    @Operation(summary = "Subscribe to a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "New subscription created", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "No topics found with provided ID", content = @Content)
    })
    @PostMapping("/{id}/join")
    public ResponseEntity<?> subscribeToTopic(@PathVariable int id, Principal principal) {
        Person person = personService.findById(principal.getName());
        Topic topic = topicService.findById(id);
        person.getTopics().add(topic);
        topicService.update(topic);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}