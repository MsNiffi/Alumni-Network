package com.example.alumninetwork.mappers;


import com.example.alumninetwork.models.dtos.gang.GangDTO;
import com.example.alumninetwork.models.dtos.gang.GangPostDTO;
import com.example.alumninetwork.models.dtos.gang.GangUpdateDTO;
import com.example.alumninetwork.models.entities.Gang;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.models.entities.Post;
import com.example.alumninetwork.services.person.PersonService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

// Mapper for groups

@Mapper(componentModel = "spring")
public abstract class GangMapper {
    @Autowired
    private PersonService personService;
    @Mapping(target = "posts", source = "posts", qualifiedByName = "postsToIds")
    @Mapping(target = "persons", source = "persons", qualifiedByName = "personsToIds")
    public abstract GangDTO gangToGangDto(Gang gang);

    @Mapping(target = "persons", source = "persons", qualifiedByName = "idsToPersons")
    public abstract Gang postDtoToGang(GangPostDTO gangPostDTO);

    public abstract Collection<GangDTO> gangToGangDto(Collection<Gang> gangs);

    @Named("postsToIds")
    Set<Integer> postsToIds(Set<Post> posts) {
        return posts.stream().map(Post::getPostId).collect(Collectors.toSet());
    }

    @Named("personsToIds")
    Set<String> personsToIds (Set<Person> persons) {
        if (persons.isEmpty()) {
            return null;
        }
        return persons.stream().map(Person::getPersonId).collect(Collectors.toSet());
    }

    @Named("idsToPersons")
    Set<Person> idsToPersons (Set<String> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream().map(id ->personService.findById(id)).collect(Collectors.toSet());
    }
}
