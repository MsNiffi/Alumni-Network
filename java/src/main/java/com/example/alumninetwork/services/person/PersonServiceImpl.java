package com.example.alumninetwork.services.person;

import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.repositories.PersonRepository;
import com.example.alumninetwork.utils.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findById(String id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public Person findPersonInGang(Integer group_id) {
        return personRepository.findPersonByGangsGangId(group_id);
    }

    @Override
    public Person findPersonInTopic(Integer topic_id) {
        return personRepository.findPersonByTopicsTopicId(topic_id);
    }

    @Override
    public Collection<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person add(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void update(Person person) {
        personRepository.save(person);
    }

    @Override
    public void deleteById(String id) {
        personRepository.deleteById(id);
    }
}
