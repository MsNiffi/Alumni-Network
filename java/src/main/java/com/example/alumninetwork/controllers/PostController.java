package com.example.alumninetwork.controllers;

import com.example.alumninetwork.mappers.PostMapper;
import com.example.alumninetwork.models.dtos.post.PostDTO;
import com.example.alumninetwork.models.dtos.post.PostDmDTO;
import com.example.alumninetwork.models.dtos.post.PostPostDTO;
import com.example.alumninetwork.models.dtos.post.PostUpdateDTO;
import com.example.alumninetwork.models.entities.Gang;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.models.entities.Post;
import com.example.alumninetwork.services.gang.GangService;
import com.example.alumninetwork.services.person.PersonService;
import com.example.alumninetwork.services.post.PostService;
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

import java.net.URI;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/posts")
@Tag(name = "Posts", description = "Endpoints to interact with posts")
public class PostController {
    private final PostMapper postMapper;
    private final PostService postService;
    private final PersonService personService;
    private final GangService gangService;

    public PostController(PostMapper postMapper, PostService postService, PersonService personService, GangService gangService) {
        this.postMapper = postMapper;
        this.postService = postService;
        this.personService = personService;
        this.gangService = gangService;
    }

    @Operation(summary = "Get all posts to groups and topics for which the requesting user is subscribed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PostDTO.class)))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping
    public ResponseEntity<?> getAllBySubscription(Principal principal) {
        String userId = principal.getName();
        Collection<Post> postsInGroups = postService.findAllByPersonGangAndTopicSubscription(userId);
        List<Post> posts = postsInGroups.stream().sorted(Comparator.comparing(Post::getUpdatedOn).reversed()).collect(Collectors.toList());
        return ResponseEntity.ok(postMapper.postToPostDto(posts));
    }

    @Operation(summary = "Get all posts of selected group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping("/group/{group_id}")
    public ResponseEntity<?> getAllByGroup(@PathVariable int group_id) {
        Collection<Post> posts = postService.findAllByGang(group_id);

        return ResponseEntity.ok(postMapper.postToPostDto(posts));
    }

    @Operation(summary = "Get all posts of selected topic")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Bad request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping("/topic/{topic_id}")
    public ResponseEntity<?> getAllByTopic(@PathVariable int topic_id) {
        Collection<Post> posts = postService.findAllByTopic(topic_id);

        return ResponseEntity.ok(postMapper.postToPostDto(posts));
    }

    @Operation(summary = "Get all posts of selected event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping("/event/{event_id}")
    public ResponseEntity<?> findAllByEvent(@PathVariable int event_id) {
        Collection<Post> posts = postService.findAllByEvent(event_id);

        return ResponseEntity.ok(postMapper.postToPostDto(posts));
    }

    @Operation(summary = "Add a post to group, topic or event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Post successfully added",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Forbidden", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostPostDTO postDTO, Principal principal) {
        Person creator = personService.findById(principal.getName());
        Set<Gang> creatorGangs = creator.getGangs();
        // Ternary operator to check if targetGang is empty or not. If empty, it will be null.
        Gang targetGang = postDTO.getTargetGang() != null ? gangService.findById(postDTO.getTargetGang()) : null;
        // Returns 403 forbidden if creator tries to post to a gang which the creator is not member of
        if (targetGang != null && !creatorGangs.contains(targetGang)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Post post = postMapper.postPostDtoToPost(postDTO);
        post.setPublishedOn(LocalDateTime.now());
        post.setUpdatedOn(LocalDateTime.now());
        post.setSender(creator);
        // Autosubscribes person to the topics
        creator.getTopics().addAll(post.getTargetTopics());
        postService.add(post);
        URI newPost = URI.create("api/v1/post");
        return ResponseEntity.created(newPost).build();
    }

    @Operation(summary = "Update a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Post successfully updated",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Post not found with supplied ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class))}),
    })
    @PutMapping("/{post_id}")
    public ResponseEntity<?> updatePost(@RequestBody PostUpdateDTO postUpdateDTO, @PathVariable int post_id, Principal principal) {
        Post post = postService.findById(post_id);
        String userId = principal.getName();
        if (!Objects.equals(post.getSender().getPersonId(), userId)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("The user is not the original author of this post, and is not allowed to edit it ");
        }
        post.setTitle(postUpdateDTO.getTitle());
        post.setBody(postUpdateDTO.getBody());
        post.setUpdatedOn(LocalDateTime.now());
        postService.update(post);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all dms to the requesting user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDmDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    })
    @GetMapping("user")
    public ResponseEntity<?> getDms(Principal principal) {
        Collection<Post> postDms = postService.findPostsByTargetPersonPersonId(principal.getName());
        Collection<PostDmDTO> dmDtos = postMapper.postDmToDto(postDms);
        return ResponseEntity.ok(dmDtos);
    }

    @Operation(summary = "Get all dms from user with user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDmDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    })
    @GetMapping("user/{personId}")
    public ResponseEntity<?> getDmsFromPerson(@PathVariable String personId, Principal principal) {
        // Gets all dms to the user
        Collection<Post> postDms = postService.findPostsByTargetPersonPersonId(principal.getName());
        // Filters the list by the sender's person ID.
        Collection<Post> filteredPostDms = postDms.stream().filter((Post p) -> Objects.equals(p.getSender().getPersonId(), personId))
                .collect(Collectors.toSet());
        Collection<PostDmDTO> filteredDms = postMapper.postDmToDto(filteredPostDms);
        return ResponseEntity.ok(filteredDms);
    }
}
