package com.example.alumninetwork.repositories;

import com.example.alumninetwork.models.dtos.post.PostDmDTO;
import com.example.alumninetwork.models.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT distinct post FROM Post post JOIN post.targetGang g JOIN g.persons u WHERE u.personId = :user_id " +
            "UNION SELECT distinct post FROM Post post JOIN post.targetTopics g JOIN g.persons u WHERE u.personId=:user_id")
    List<Post> findAllByUserGroupAndTopicSubscription(String user_id);

    List<Post> findPostsByTargetGangGangId(int group_id);

    List<Post> findPostsByTargetTopicsTopicId(int topic_id);

    List<Post> findPostsByTargetEventEventId(int event_id);

    List<Post> findPostsByTargetPersonPersonId(String personId);
}
