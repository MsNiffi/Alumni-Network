package com.example.alumninetwork.services.post;

import com.example.alumninetwork.models.entities.Post;
import com.example.alumninetwork.repositories.PostRepository;
import com.example.alumninetwork.utils.exceptions.PostNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post findById(Integer id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    public Collection<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post add(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void update(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    public Collection<Post> findAllByPersonGangAndTopicSubscription(String user_id) {
        List<Post> posts = postRepository.findAllByUserGroupAndTopicSubscription(user_id);
        posts.sort(Comparator.comparing(Post::getUpdatedOn));

        return posts;
    }

    @Override
    public Collection<Post> findAllByGang(int group_id) {
        List<Post> posts = postRepository.findPostsByTargetGangGangId(group_id);
        posts.sort(Comparator.comparing(Post::getUpdatedOn));

        return posts;
    }

    @Override
    public Collection<Post> findAllByTopic(int topic_id) {
        List<Post> posts = postRepository.findPostsByTargetTopicsTopicId(topic_id);
        posts.sort(Comparator.comparing(Post::getUpdatedOn));

        return posts;
    }

    @Override
    public Collection<Post> findAllByEvent(int event_id) {
        List<Post> posts = postRepository.findPostsByTargetEventEventId(event_id);
        posts.sort(Comparator.comparing(Post::getUpdatedOn));

        return posts;
    }

    @Override
    public Collection<Post> findPostsByTargetPersonPersonId(String personId) {
        return postRepository.findPostsByTargetPersonPersonId(personId);
    }
}
