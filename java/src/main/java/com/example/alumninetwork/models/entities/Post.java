package com.example.alumninetwork.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    @Column(length = 40, nullable = false)
    private String title;
    @Column(length = 500, nullable = false)
    private String body;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
    @ManyToOne
    private Person sender;
    @ManyToOne
    @JoinColumn(name = "replyParent")
    private Post replyParent;
    @OneToMany(mappedBy = "replyParent")
    private Set<Post> replies;
    @ManyToOne
    private Gang targetGang;
    @ManyToMany
    @JoinTable(name = "topic_post", joinColumns = {@JoinColumn(name = "post_id")}, inverseJoinColumns = {@JoinColumn(name = "topic_id")})
    private Set<Topic> targetTopics;
    @ManyToOne
    private Event targetEvent;
    @ManyToOne
    private Person targetPerson;
}
