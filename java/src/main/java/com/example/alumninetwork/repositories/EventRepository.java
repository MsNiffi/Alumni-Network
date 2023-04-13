package com.example.alumninetwork.repositories;

import com.example.alumninetwork.models.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query(value = """
            SELECT DISTINCT e.* FROM event e
            INNER JOIN event_gang eg on e.event_id = eg.event_id
            INNER JOIN person_gang pg on eg.gang_id = pg.gang_id
            UNION SELECT DISTINCT e.* FROM event e
            INNER JOIN event_topic et on e.event_id = et.event_id
            INNER JOIN person_topic pt on et.topic_id = pt.topic_id
            WHERE person_id = '?1'""", nativeQuery = true)
    Collection<Event> findEventsByPersonSubscribedToTopicsAndGroupsByPersonId(String id);

}
