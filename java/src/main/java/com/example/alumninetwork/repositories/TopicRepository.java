package com.example.alumninetwork.repositories;

import com.example.alumninetwork.models.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
