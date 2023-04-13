package com.example.alumninetwork.services.topic;

import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.models.entities.Topic;
import com.example.alumninetwork.repositories.TopicRepository;
import com.example.alumninetwork.utils.exceptions.TopicNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Topic findById(Integer id) {
        return topicRepository.findById(id).orElseThrow(() -> new TopicNotFoundException(id));
    }

    @Override
    public Collection<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Override
    public Topic add(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public void update(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public Person findPersonInTopic(String user_id, int topic_id) {
        Topic topic = this.findById(topic_id);
        return topic.getPersons().stream().filter(u -> Objects.equals(u.getPersonId(), user_id)).findFirst().orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        topicRepository.deleteById(id);
    }

}
