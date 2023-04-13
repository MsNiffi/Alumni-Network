package com.example.alumninetwork.controllers;

import com.example.alumninetwork.mappers.GangMapper;
import com.example.alumninetwork.models.dtos.gang.GangDTO;
import com.example.alumninetwork.models.dtos.gang.GangPostDTO;
import com.example.alumninetwork.models.entities.Gang;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.services.gang.GangService;
import com.example.alumninetwork.services.person.PersonService;
import com.example.alumninetwork.utils.error.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/groups")
@Tag(name = "Groups", description = "Endpoints to interact with groups")
public class GangController {
    private final GangMapper gangMapper;
    private final GangService gangService;
    private final PersonService personService;

    public GangController(GangMapper gangMapper, GangService gangService, PersonService personService) {
        this.gangMapper = gangMapper;
        this.gangService = gangService;
        this.personService = personService;
    }

    @Operation(summary = "Get a group by id, return 403 forbidden if user is not a member of requested group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gang.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "The user is not a member of requested group",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Group does not exist with supplied ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),

    })
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable int id, Principal principal) {
        String personId = principal.getName();
        Gang selectedGang = gangService.findById(id);
        if (gangService.findPersonInGang(personId, selectedGang.getGangId()) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not a member of requested group");
        }
        GangDTO gang = gangMapper.gangToGangDto(selectedGang);
        return ResponseEntity.ok(gang);
    }

    @Operation(summary = "Get all groups that are public as well as private groups the user is a member of")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Gang.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping
    public ResponseEntity<?> findAllExcludePrivateUserNotMember(Principal principal) {
        String personId = principal.getName();
        List<Gang> gangs = gangService.findAllExcludePrivateGangsThatPersonIsNotMember(personId);
        return ResponseEntity.ok(gangMapper.gangToGangDto(gangs));
    }

    @Operation(summary = "Creates a group, and add the current user to that group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Group successfully created",
                    content = @Content),

            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createAndJoinGang(@RequestBody GangPostDTO gangPostDTO, Principal principal) {
        Gang gang = gangMapper.postDtoToGang(gangPostDTO);
        Person person = personService.findById(principal.getName());
        person.getGangs().add(gang);
        gangService.add(gang);
        URI newGang = URI.create("api/v1/groups");
        return ResponseEntity.created(newGang).build();
    }

    @Operation(summary = "Add a user to a group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Group successfully created",
                    content = @Content),

            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Only members of a private group may request access", content = @Content)
    })
    @PostMapping({"/{group_id}/join", })
    public ResponseEntity<?> joinGroup(@PathVariable int group_id, Principal principal) {
        String user_id = principal.getName();
        Gang gang = gangService.findById(group_id);
        if(gang.getIsPrivate()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only members of a private group may request access");
        }
        //user join to public group
        gangService.addPersonToGang(user_id, group_id);
        return ResponseEntity.noContent().build();
    }
}