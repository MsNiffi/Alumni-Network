package com.example.alumninetwork.repositories;

import com.example.alumninetwork.models.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    Person findPersonByGangsGangId(Integer group_id);
    Person findPersonByTopicsTopicId(Integer topic_id);
}
