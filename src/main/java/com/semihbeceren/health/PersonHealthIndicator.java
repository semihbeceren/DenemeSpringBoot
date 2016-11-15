package com.semihbeceren.health;

import com.semihbeceren.model.Person;
import com.semihbeceren.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by Semih Beceren on 15.11.2016.
 */
@Component
public class PersonHealthIndicator implements HealthIndicator{
    @Autowired
    PersonService personService;


    @Override
    public Health health() {
        Collection<Person> persons = personService.findAll();

        if(persons == null || persons.size() == 0){
            return Health.down().withDetail("count", 0).build();
        }

        return Health.up().withDetail("count", persons.size()).build();
    }
}
