package com.example.alumninetwork.services.post;

import com.example.alumninetwork.models.dtos.post.PostDmDTO;
import com.example.alumninetwork.models.entities.Post;
import com.example.alumninetwork.services.CRUDService;

import java.util.Collection;
import java.util.Set;


public interface PostService extends CRUDService<Post, Integer> {
    /**
     * Find all posts of all groups and topics the user has subscribed.
     *
     * @param user_id
     * @return Collection of Post objects
     */
    Collection<Post> findAllByPersonGangAndTopicSubscription(String user_id);

    /**
     * Find all posts with target group specified by the ID
     *  @param group_id
     * @return Collection of Post objects
     */
    Collection<Post> findAllByGang(int group_id);

    /**
     * Find all posts with target topic specified by the ID
     *  @param topic_id
     * @return Collection of Post objects
     */
    Collection<Post> findAllByTopic(int topic_id);

    /**
     * Find all posts with target event specified by the ID
     *  @param event_id
     * @return Collection of Post objects
     */
    Collection<Post> findAllByEvent(int event_id);

    Collection<Post> findPostsByTargetPersonPersonId (String personId);
}
