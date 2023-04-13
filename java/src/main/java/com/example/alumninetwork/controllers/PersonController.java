package com.example.alumninetwork.controllers;

import com.example.alumninetwork.mappers.PersonMapper;
import com.example.alumninetwork.models.dtos.person.PersonDTO;
import com.example.alumninetwork.models.dtos.person.PersonPostDTO;
import com.example.alumninetwork.models.dtos.person.PersonUpdateDTO;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.services.person.PersonService;
import com.example.alumninetwork.utils.error.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/users")
@Tag(name = "Users", description = "Endpoints to interact with users")
public class PersonController {
    private final PersonMapper personMapper;
    private final PersonService personService;

    public PersonController(PersonMapper personMapper, PersonService personService) {
        this.personMapper = personMapper;
        this.personService = personService;
    }

    @Operation(summary = "Return currently logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content),
            @ApiResponse(responseCode = "303", description = "Redirect to user/{user_id}", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    })
    @GetMapping()
    public ResponseEntity<String> getCurrentUser(Principal principal) {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String id = principal.getName();
        URI location = URI.create(baseUrl + "/api/v1/users/" + id);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(location);
        return new ResponseEntity<>("", responseHeaders, HttpStatus.SEE_OTHER);
    }

    @Operation(summary = "Return user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "No topics found", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return ResponseEntity.ok(personMapper.personToPersonDto(personService.findById(id)));
    }

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User successfully created",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    })
    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody PersonPostDTO personPostDTO, Principal principal) {
        Person personToPost = personMapper.personPostDtoToPerson(personPostDTO);
        if (Objects.equals(personPostDTO.getPersonId(), principal.getName())) {
            personService.add(personToPost);
            URI newPost = URI.create("api/v1/users" + personToPost.getPersonId());
            return ResponseEntity.created(newPost).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User successfully updated",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PatchMapping
    public ResponseEntity<?> patchPerson(@RequestBody PersonUpdateDTO person, Principal principal) {
        Person personToPatch = personService.findById(principal.getName());
        if (Objects.equals(person.getPersonId(), principal.getName())) {
            personToPatch.setBio(person.getBio());
            personToPatch.setFirstName(person.getFirstName());
            personToPatch.setLastName(person.getLastName());
            personToPatch.setFunFact(person.getFunFact());
            personToPatch.setUserName(person.getUserName());
            personToPatch.setWorkStatus(person.getWorkStatus());
            personToPatch.setPictureUrl(person.getPictureUrl());
            personService.update(personToPatch);
            URI newUpdate = URI.create("api/v1/users" + personToPatch.getPersonId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Health check")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping("health")
    public ResponseEntity<?> health() {
        Map<String, String> map = new HashMap<>();
        map.put("status", "ok");
        map.put("message", "response body");
        return ResponseEntity.ok(map
        );
    }
}
