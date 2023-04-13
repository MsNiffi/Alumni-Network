package com.example.alumninetwork.mappers;

import com.example.alumninetwork.models.dtos.person.PersonDTO;
import com.example.alumninetwork.models.dtos.person.PersonPostDTO;
import com.example.alumninetwork.models.dtos.person.PersonUpdateDTO;
import com.example.alumninetwork.models.entities.Event;
import com.example.alumninetwork.models.entities.Gang;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.models.entities.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

// Mapper for users

@Mapper(componentModel = "spring")
public abstract class PersonMapper {
    @Mapping(target = "topics", source = "topics", qualifiedByName = "topicsToIds")
    @Mapping(target = "gangs", source = "gangs", qualifiedByName = "gangsToIds")
    @Mapping(target = "events", source = "events", qualifiedByName = "eventsToIds")
    public abstract PersonDTO personToPersonDto (Person person);
    public abstract Person personPostDtoToPerson(PersonPostDTO userDto);

    public abstract Person personUpdateDtoToPerson(PersonUpdateDTO personUpdateDTO);

    @Named("topicsToIds")
    public Set<Integer> topicsToIds (Set<Topic> topics) {
        return topics.stream().map(Topic::getTopicId).collect(Collectors.toSet());
    }

    @Named("gangsToIds")
    public Set<Integer> gangsToIds (Set<Gang> gangs) {
        return gangs.stream().map(Gang::getGangId).collect(Collectors.toSet());
    }

    @Named("eventsToIds")
    public Set<Integer> eventsToIds (Set<Event> events) {
        return events.stream().map(Event::getEventId).collect(Collectors.toSet());
    }
}
