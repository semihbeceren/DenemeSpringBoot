package com.semihbeceren.service;

import com.semihbeceren.model.Person;

import java.util.Collection;

/**
 * Created by Semih Beceren on 11.11.2016.
 */
public interface PersonService {
    Collection<Person> findAll();

    Person findOne(Long id);

    Person create(Person person);

    Person update(Person person);

    void delete(Long id);

    void evictAllCaches();
}
