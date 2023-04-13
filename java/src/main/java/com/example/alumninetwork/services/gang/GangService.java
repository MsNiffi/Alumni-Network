package com.example.alumninetwork.services.gang;

import com.example.alumninetwork.models.entities.Gang;
import com.example.alumninetwork.models.entities.Person;
import com.example.alumninetwork.services.CRUDService;

import java.util.List;

public interface GangService extends CRUDService<Gang, Integer> {

    List<Gang> findAllExcludePrivateGangsThatPersonIsNotMember(String id);
    Person findPersonInGang(String personId, int gangId);
    Gang add(Gang gang);
    void addPersonToGang(String personId, int groupId);
}
