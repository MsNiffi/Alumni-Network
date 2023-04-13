package com.example.alumninetwork.services.person;

import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.services.CRUDService;


public interface PersonService extends CRUDService<Person, String> {

    /**
     * Find person in group by ID
     *  @param group_id
     * @return Collection of Person objects
     */
    Person findPersonInGang(Integer group_id);

    /**
     * Find person in topic by ID
     *  @param topic_id
     * @return Collection of Person objects
     */
    Person findPersonInTopic(Integer topic_id);
}
