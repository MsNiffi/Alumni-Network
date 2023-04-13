package com.example.alumninetwork.services.gang;

import com.example.alumninetwork.models.entities.Gang;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.repositories.GangRepository;
import com.example.alumninetwork.services.person.PersonService;
import com.example.alumninetwork.utils.exceptions.GangNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GangServiceImpl implements GangService {
    private final GangRepository gangRepository;
    private final PersonService personService;

    public GangServiceImpl(GangRepository gangRepository, PersonService personService) {
        this.gangRepository = gangRepository;
        this.personService = personService;
    }

    @Override
    public Gang add(Gang gang) {
        return gangRepository.save(gang);
    }

    @Override
    public void update(Gang gang) {
        gangRepository.save(gang);
    }

    @Override
    public Gang findById(Integer id) {
        return gangRepository.findById(id).orElseThrow(() -> new GangNotFoundException(id));
    }

    @Override
    public Collection<Gang> findAll() {
        return gangRepository.findAll();
    }

    //find all public groups and those private groups requesting user is a member of.
    @Override
    public List<Gang> findAllExcludePrivateGangsThatPersonIsNotMember(String id) {
        Person person = this.personService.findById(id);

        return gangRepository.findAll().stream()
                .filter(g -> !g.getIsPrivate() || this.findPersonInGang(person.getPersonId(), g.getGangId()) != null).collect(Collectors.toList());
    }

    @Override
    public Person findPersonInGang(String personId, int gangId){
        Gang gang = this.findById(gangId);
        return gang.getPersons().stream().filter(u -> Objects.equals(u.getPersonId(), personId)).findFirst().orElse(null);
    }

    @Override
    public void addPersonToGang(String personId, int groupId){
        Gang gang = this.findById(groupId);
        Person person = this.personService.findById(personId);
        person.getGangs().add(gang);
        this.update(gang);
    }

    @Override
    @Transactional
    public void deleteById(Integer group_id) {
        if (gangRepository.existsById(group_id)){
            gangRepository.deleteById(group_id);
        }
    }
}

