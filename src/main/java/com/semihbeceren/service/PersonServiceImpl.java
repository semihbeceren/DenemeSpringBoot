package com.semihbeceren.service;

import com.semihbeceren.model.Person;
import com.semihbeceren.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Semih Beceren on 11.11.2016.
 */

@Service
@Transactional
//Parametresiz Transactional notasyonu bu değerleri default olarak sağlar = @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CounterService counterService;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Collection<Person> findAll() {
        counterService.increment("method.invoked.personServiceImpl.findAll");
        Collection<Person> persons = personRepository.findAll();
        return persons;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable(value = "persons", key = "#id")
    public Person findOne(Long id) {
        Person person = personRepository.findOne(id);
        return person;
    }

    @Override
    @CachePut(value = "persons", key = "#result.id")
    public Person create(Person person) {
        //Id alanı boş olmalı, database üzerinde aynı id değerine sahip bir kayıt girilmemeli aksi durumda update olarak işlem yapılır.
        if(person.getId() != null){
            return null;
        }

        Person createdPerson = personRepository.save(person);
        return createdPerson;
    }

    @Override
    @CachePut(value = "persons", key = "#person.id")
    public Person update(Person person) {
        Person personToUpdate = personRepository.findOne(person.getId());
        //Update edilecek kayıt bulunmuş olmalı.
        if(personToUpdate == null){
            return null;
        }

        Person updatedPerson = personRepository.save(person);
        return updatedPerson;
    }

    @Override
    @CacheEvict(value = "persons", key = "#id")
    public void delete(Long id) {
        personRepository.delete(id);
    }

    @Override
    @CacheEvict(value = "persons", allEntries = true)
    public void evictAllCaches() {

    }
}
