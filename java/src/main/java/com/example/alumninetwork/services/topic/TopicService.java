package com.example.alumninetwork.services.topic;

import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.models.entities.Topic;
import com.example.alumninetwork.services.CRUDService;

import java.util.Collection;

public interface TopicService extends CRUDService<Topic, Integer> {
    /**
     * Find user in selected topic member records
     * @param user_id
     * @param topic_id
     * @return
     */
    Person findPersonInTopic(String user_id, int topic_id);

}
