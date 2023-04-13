package com.example.alumninetwork.services.event;

import com.example.alumninetwork.models.entities.Event;
import com.example.alumninetwork.services.CRUDService;

import java.util.Collection;

public interface EventService extends CRUDService<Event, Integer> {
    Collection<Event> findEventsByPersonSubscribedToTopicsAndGangsByPersonId(String id);

    /**
     * Create a new event group invite
     * @param group_id
     * @param event_id
     */
    void createGroupInvite(Integer group_id, Integer event_id);

    /**
     * Delete a group invite. Does not delete RSVP records.
     * @param group_id
     * @param event_id
     */
    void deleteGroupInvite(Integer group_id, Integer event_id);

    /**
     * Create a new event topic invite
     * @param topic_id
     * @param event_id
     */
    void createTopicInvite(Integer topic_id, Integer event_id);

    /**
     * Delete a topic invite. Does not delete RSVP records.
     * @param topic_id
     * @param event_id
     */
    void deleteTopicInvite(Integer topic_id, Integer event_id);

    /**
     * Create a new event user invite
     * @param user_id
     * @param event_id
     */
    void createUserInvite(String user_id, Integer event_id);

    /**
     * Delete a user invite. Does not delete RSVP records.
     * @param user_id
     * @param event_id
     */
    void deleteUserInvite(String user_id, Integer event_id);
}
