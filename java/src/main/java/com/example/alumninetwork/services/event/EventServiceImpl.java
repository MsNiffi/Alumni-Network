package com.example.alumninetwork.services.event;

import com.example.alumninetwork.models.entities.Event;
import com.example.alumninetwork.models.entities.Gang;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.models.entities.Topic;
import com.example.alumninetwork.repositories.EventRepository;
import com.example.alumninetwork.repositories.GangRepository;
import com.example.alumninetwork.repositories.PersonRepository;
import com.example.alumninetwork.repositories.TopicRepository;
import com.example.alumninetwork.services.gang.GangService;
import com.example.alumninetwork.services.person.PersonService;
import com.example.alumninetwork.services.person.PersonServiceImpl;
import com.example.alumninetwork.services.topic.TopicService;
import com.example.alumninetwork.utils.exceptions.EventNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final GangRepository gangRepository;
    private final TopicRepository topicRepository;
    private final GangService gangService;
    private final TopicService topicService;
    private final PersonService personService;

    private final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final PersonRepository personRepository;


    public EventServiceImpl(EventRepository eventRepository,
                            GangRepository gangRepository, GangService gangService,
                            TopicRepository topicRepository, TopicService topicService, PersonService personService,
                            PersonRepository personRepository) {
        this.eventRepository = eventRepository;
        this.gangRepository = gangRepository;
        this.gangService = gangService;
        this.topicRepository = topicRepository;
        this.topicService = topicService;
        this.personService = personService;
        this.personRepository = personRepository;
    }

    @Override
    public Event findById(Integer id) {
        return eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    @Override
    public Collection<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event add(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void update(Event event) {
        eventRepository.save(event);
    }

    @Override
    public void deleteById(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Collection<Event> findEventsByPersonSubscribedToTopicsAndGangsByPersonId(String id) {
        return eventRepository.findEventsByPersonSubscribedToTopicsAndGroupsByPersonId(id);
    }

    @Override
    public void createGroupInvite(Integer group_id, Integer event_id) {
        if (gangRepository.existsById(group_id) && eventRepository.existsById(event_id)) {
            Gang gang = gangService.findById(group_id);
            Event event = findById(event_id);
            Set<Gang> eventGroups = event.getGangs()==null? new HashSet<>():event.getGangs();
            eventGroups.add(gang);
            event.setGangs(eventGroups);
            update(event);
        }
    }

    @Override
    public void deleteGroupInvite(Integer group_id, Integer event_id) {
        if (gangRepository.existsById(group_id) && eventRepository.existsById(event_id)){
            Event event = findById(event_id);
            Gang gang = gangService.findById(group_id);
            Set<Gang> eventGroups = event.getGangs().stream()
                    .filter(group1 -> !group1.equals(gang)).collect(Collectors.toSet());
            if (event.getGangs().size() == eventGroups.size()) logger.warn("Invitation not found");
            else {
                event.setGangs(eventGroups);
                update(event);
            }
        }
    }

    @Override
    public void createTopicInvite(Integer topic_id, Integer event_id) {
        if (topicRepository.existsById(topic_id) && eventRepository.existsById(event_id)){
            Event event = findById(event_id);
            Topic topic = topicService.findById(topic_id);
            Set<Topic> eventTopics = event.getEventTopics()==null? new HashSet<>():event.getEventTopics();
            eventTopics.add(topic);
            event.setEventTopics(eventTopics);
            update(event);
        }
    }

    @Override
    public void deleteTopicInvite(Integer topic_id, Integer event_id) {
        if (topicRepository.existsById(topic_id) && eventRepository.existsById(event_id)){
            Event event = findById(event_id);
            Topic topic = topicService.findById(topic_id);
            Set<Topic> eventTopics = event.getEventTopics().stream()
                    .filter(t -> !t.equals(topic)).collect(Collectors.toSet());
            if (event.getEventTopics().size() == eventTopics.size()) logger.warn("Invitation not found");
            else {
                event.setEventTopics(eventTopics);
                update(event);
            }
        }
    }

    @Override
    public void createUserInvite(String user_id, Integer event_id) {
        if (personRepository.existsById(user_id) && eventRepository.existsById(event_id)){
            Event event = findById(event_id);
            Person user = personService.findById(user_id);
            Set<Person> eventUserInvites = event.getEventUserInvite() == null? new HashSet<>():event.getEventUserInvite();
            eventUserInvites.add(user);
            event.setEventUserInvite(eventUserInvites);
            update(event);
        }
    }

    @Override
    public void deleteUserInvite(String user_id, Integer event_id) {
        if (personRepository.existsById(user_id) && eventRepository.existsById(event_id)){
            Event event = findById(event_id);
            Person user = personService.findById(user_id);
            Set<Person> eventUserInvites = event.getEventUserInvite().stream()
                    .filter(eui->!eui.equals(user)).collect(Collectors.toSet());
            if (event.getEventUserInvite().size() == eventUserInvites.size()) logger.warn("Invitation not found");
            else {
                event.setEventUserInvite(eventUserInvites);
                update(event);
            }
        }
    }
}
