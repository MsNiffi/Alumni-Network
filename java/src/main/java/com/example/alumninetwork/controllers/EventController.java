package com.example.alumninetwork.controllers;

import com.example.alumninetwork.mappers.EventMapper;
import com.example.alumninetwork.models.dtos.event.EventDTO;
import com.example.alumninetwork.models.dtos.event.EventPostDTO;
import com.example.alumninetwork.models.dtos.event.EventUpdateDTO;
import com.example.alumninetwork.models.entities.Event;
import com.example.alumninetwork.models.entities.Gang;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.models.entities.Topic;
import com.example.alumninetwork.services.event.EventService;
import com.example.alumninetwork.services.gang.GangService;
import com.example.alumninetwork.services.person.PersonService;
import com.example.alumninetwork.services.topic.TopicService;
import com.example.alumninetwork.utils.error.ApiErrorResponse;
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
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/events")
@Tag(name = "Events", description = "Endpoints to interact with events")
public class EventController {
    private final EventMapper eventMapper;
    private final EventService eventService;
    private final GangService gangService;
    private final TopicService topicService;
    private final PersonService personService;

    public EventController(EventMapper eventMapper, EventService eventService, GangService gangService, TopicService topicService, PersonService personService) {
        this.eventMapper = eventMapper;
        this.eventService = eventService;
        this.gangService = gangService;
        this.topicService = topicService;
        this.personService = personService;
    }

    @Operation(summary = "Return all relevant events for requesting user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EventDTO.class)))}),
            @ApiResponse(responseCode = "400", description = "Malformed request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "No events found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getEventsBySubscription(Principal principal) {
        String personId = principal.getName();
        return ResponseEntity.ok(eventMapper.eventToEventDto(eventService.findEventsByPersonSubscribedToTopicsAndGangsByPersonId(personId)));
    }

    @Operation(summary = "Create a new event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New topic created and creator subscribed to it",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventPostDTO eventPostDTO, Principal principal) {
        Person creator = personService.findById(principal.getName());
        Event event = eventMapper.eventPostDtoToEvent(eventPostDTO);
        Set<Gang> eventGangs = event.getGangs();
        Set<Topic> eventTopics = event.getEventTopics();
        // Checks if the user is in the target group or topic and returns true if so
        AtomicBoolean userInGang = new AtomicBoolean(false);
        AtomicBoolean userInTopic = new AtomicBoolean(false);
        if (eventGangs != null) {
            eventGangs.forEach(gang -> userInGang.set(gang.getPersons().contains(creator)));
        }
        if (eventTopics != null) {
            eventTopics.forEach(topic -> userInTopic.set(topic.getPersons().contains(creator)));
        }

        if (eventGangs != null || eventTopics != null) {
            if (!userInGang.get() && !userInTopic.get()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not part of the target group and/or subscribed to the target topic");
            }
        }

        event.setCreatorId(creator);
        event.setTimeCreated(LocalDateTime.now());
        event.setTimeUpdated(LocalDateTime.now());
        creator.getEvents().add(event);
        eventService.add(event);
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        URI location = URI.create(baseUrl + "/api/v1/event/" + event.getEventId());
        return ResponseEntity.created(location).body(event.getEventId());
    }

    @Operation(summary = "Update an event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Malformed request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only the event creator can edit events", content = @Content)
    })
    @PutMapping("{id}")
    public ResponseEntity<?> updateEvent(@RequestBody EventUpdateDTO eventUpdateDTO, Principal principal, @PathVariable int id) {
        Event eventToUpdate = eventService.findById(id);
        if (eventToUpdate.getCreatorId().getPersonId().equals(principal.getName())) {
            eventToUpdate.setEventDescription(eventUpdateDTO.getEventDescription());
            eventToUpdate.setEventName(eventUpdateDTO.getEventName());
            eventToUpdate.setTimeUpdated(LocalDateTime.now());
            eventToUpdate.setStartTime(eventUpdateDTO.getStartTime());
            eventToUpdate.setEndTime(eventUpdateDTO.getEndTime());
            eventToUpdate.setAllowGuests(eventUpdateDTO.getAllowGuests());
            eventService.update(eventToUpdate);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Create new invitation to a group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Group invitation created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Malformed request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only event creator can create invitations", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event or group not found", content = @Content),
    })
    @PostMapping(path = "/{event_id}/invite/group/{group_id}")
    public ResponseEntity<?> newGroupInvitation(
            @PathVariable int event_id,
            @PathVariable int group_id,
            Principal principal) {
        Event event = eventService.findById(event_id);
        gangService.findById(group_id);
        String requestingUser = principal.getName();
        if (!event.getCreatorId().getPersonId().equals(requestingUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only event creator can create invitations");
        } else {
            eventService.createGroupInvite(group_id, event_id);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete group invitation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Group invitation deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Malformed request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only event creator can delete invitations", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event or group not found", content = @Content),
    })
    @DeleteMapping(path = "/{event_id}/invite/group/{group_id}")
    public ResponseEntity<?> deleteGroupInvitation(
            @PathVariable int event_id,
            @PathVariable int group_id,
            Principal principal) {
        Event event = eventService.findById(event_id);
        gangService.findById(group_id);
        String requestingUser = principal.getName();
        if (!event.getCreatorId().getPersonId().equals(requestingUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only event creator can delete invitations");
        } else {
            eventService.deleteGroupInvite(group_id, event_id);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new invitation to a topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Topic invitation created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Malformed request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only event creator can create invitations", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event or topic not found", content = @Content),
    })
    @PostMapping(path = "/{event_id}/invite/topic/{topic_id}")
    public ResponseEntity<?> newTopicInvitation(
            @PathVariable int event_id,
            @PathVariable int topic_id,
            Principal principal) {
        Event event = eventService.findById(event_id);
        topicService.findById(topic_id);
        String requestingUser = principal.getName();
        if (!event.getCreatorId().getPersonId().equals(requestingUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only event creator can send invitations");
        } else {
            eventService.createTopicInvite(topic_id, event_id);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete topic invitation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Topic invitation deleted", content = @Content),
            @ApiResponse(responseCode = "400", description = "Malformed request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only event creator can delete invitations", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event or topic not found", content = @Content),
    })
    @DeleteMapping(path = "/{event_id}/invite/topic/{topic_id}")
    public ResponseEntity<?> deleteTopicInvitation(
            @PathVariable int event_id,
            @PathVariable int topic_id,
            Principal principal) {
        Event event = eventService.findById(event_id);
        topicService.findById(topic_id);
        String requestingUser = principal.getName();
        if (!event.getCreatorId().getPersonId().equals(requestingUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only event creator can delete invitations");
        } else {
            eventService.deleteTopicInvite(topic_id, event_id);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new invitation to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User invitation created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Malformed request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only event creator can create invitations", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event or user not found", content = @Content),
    })
    @PostMapping(path = "/{event_id}/invite/user/{user_id}")
    public ResponseEntity<?> newUserInvitation(
            @PathVariable int event_id,
            @PathVariable String user_id,
            Principal principal) {
        Event event = eventService.findById(event_id);
        personService.findById(user_id);
        String requestingUser = principal.getName();
        if (!event.getCreatorId().getPersonId().equals(requestingUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only event creator can send invitations");
        } else eventService.createUserInvite(user_id, event_id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete user invitation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success", content = @Content),
            @ApiResponse(responseCode = "400", description = "Malformed request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only event creator can delete invitations", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event or user not found", content = @Content),
    })
    @DeleteMapping(path = "/{event_id}/invite/user/{user_id}")
    public ResponseEntity<?> deleteUserInvitation(
            @PathVariable int event_id,
            @PathVariable String user_id,
            Principal principal) {
        Event event = eventService.findById(event_id);
        personService.findById(user_id);
        String requestingUser = principal.getName();
        if (!event.getCreatorId().getPersonId().equals(requestingUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only event creator can delete invitations");
        } else eventService.deleteUserInvite(user_id, event_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create new RSVP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "RSVP created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Malformed request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Can't respond to an event without a valid invitation", content = @Content),
            @ApiResponse(responseCode = "404", description = "Event not found", content = @Content),
    })
    @PostMapping("{event_id}/rsvp")
    public ResponseEntity<?> createRSVP(@PathVariable int event_id, Principal principal) {
        Person currentUser = personService.findById(principal.getName());
        Event reqEvent = eventService.findById(event_id);
        Set<Gang> eventGangs = reqEvent.getGangs();
        Set<Topic> eventTopics = reqEvent.getEventTopics();

        // Checks if the user is in an invited gang or topic and returns true if so
        AtomicBoolean userInGang = new AtomicBoolean(false);
        AtomicBoolean userInTopic = new AtomicBoolean(false);
        eventGangs.forEach(gang -> userInGang.set(gang.getPersons().contains(currentUser)));
        eventTopics.forEach(topic -> userInTopic.set(topic.getPersons().contains(currentUser)));

        if (!currentUser.getEventUserInvite().contains(reqEvent) && !userInGang.get() && !userInTopic.get()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have an event invite and/or are not part of an invited group or subscribed to an invited topic");
        }

        // Adds the requesting user to the event described by reqEvent
        currentUser.getEvents().add(reqEvent);
        reqEvent.setTimeUpdated(LocalDateTime.now());
        eventService.add(reqEvent);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}