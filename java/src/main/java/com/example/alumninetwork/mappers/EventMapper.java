package com.example.alumninetwork.mappers;

import com.example.alumninetwork.models.dtos.event.EventDTO;
import com.example.alumninetwork.models.dtos.event.EventPostDTO;
import com.example.alumninetwork.models.dtos.event.EventUpdateDTO;
import com.example.alumninetwork.models.entities.Event;
import com.example.alumninetwork.models.entities.Gang;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.models.entities.Topic;
import com.example.alumninetwork.services.gang.GangService;
import com.example.alumninetwork.services.person.PersonService;
import com.example.alumninetwork.services.topic.TopicService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

// Mapper for events

@Mapper(componentModel = "spring")
public abstract class EventMapper {
    @Autowired
    private PersonService personService;
    @Autowired
    private GangService gangService;
    @Autowired
    private TopicService topicService;

    public abstract Collection<EventDTO> eventToEventDto(Collection<Event> event);
    @Mapping(target = "gangs", source = "gangs", qualifiedByName = "gangIdsToGangs")
    @Mapping(target = "eventTopics", source = "eventTopics", qualifiedByName = "topicsIdsToTopics")
    @Mapping(target = "persons", source = "persons", qualifiedByName = "personIdsToPersons")
    public abstract Event eventPostDtoToEvent(EventPostDTO eventPostDTO);

    public abstract Event eventUpdateToEventUpdateDto(EventUpdateDTO eventUpdateDTO);

    String map(Person person) {
        return person.getPersonId();
    }
    @Named("gangIdsToGangs")
    Set<Gang> gangIdsToGangs(Set<Integer> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream().map(id -> gangService.findById(id)).collect(Collectors.toSet());
    }

    @Named("topicsIdsToTopics")
    Set<Topic> topicsIdsToTopics(Set<Integer> ids) {
        if (ids == null ) {
            return null;
        }
        return ids.stream().map(id -> topicService.findById(id)).collect(Collectors.toSet());
    }
    @Named("personIdsToPersons")
    Set<Person> personIdsToPersons (Set<String> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream().map(id -> personService.findById(id)).collect(Collectors.toSet());
    }
}
