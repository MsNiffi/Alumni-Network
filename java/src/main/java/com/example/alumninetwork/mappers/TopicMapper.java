package com.example.alumninetwork.mappers;

import com.example.alumninetwork.models.dtos.topic.TopicDTO;
import com.example.alumninetwork.models.dtos.topic.TopicPostDTO;
import com.example.alumninetwork.models.entities.Post;
import com.example.alumninetwork.models.entities.Topic;
import com.example.alumninetwork.services.post.PostService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

// Mapper for topics

@Mapper(componentModel = "spring")
public abstract class TopicMapper {
    @Autowired
    private PostService postService;

    // Mappings from topic to DTO
    @Mapping(source = "posts", target = "posts", qualifiedByName = "postsToIds")
    public abstract TopicDTO topicToTopicDto(Topic topic);

    public abstract Collection<TopicDTO> topicToTopicDto(Collection<Topic> topics);

    public abstract Topic topicPostDtoToTopic(TopicPostDTO topicPostDTO);

    @Named("postsToIds")
    Set<Integer> postsToIds(Set<Post> posts) {
        return posts.stream().map(Post::getPostId).collect(Collectors.toSet());
    }
}
