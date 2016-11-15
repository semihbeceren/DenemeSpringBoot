package com.semihbeceren.controller;

import com.semihbeceren.model.Person;
import com.semihbeceren.service.EmailService;
import com.semihbeceren.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.concurrent.Future;

/**
 * Created by Semih Beceren on 11.11.2016.
 */
@RestController
@RequestMapping(value = "/api/persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<Person>> getPersons(){
        Collection<Person> persons = personService.findAll();
        return new ResponseEntity<Collection<Person>>(persons, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> getPerson(@PathVariable("id") Long id){
        Person person = personService.findOne(id);
        if(person == null){
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        Person createdPerson = personService.create(person);
        return new ResponseEntity<Person>(createdPerson, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> updatePerson(@RequestBody Person person){
        Person updatedPerson = personService.update(person);
        if(updatedPerson == null){
            return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Person>(updatedPerson, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> deletePerson(@PathVariable("id") Long id){
        personService.delete(id);
        return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}/send", method = RequestMethod.POST)
    public ResponseEntity<Person> sendPerson(@PathVariable("id") Long id, @RequestParam(value = "wait", defaultValue = "false") boolean waitForAsyncResult){
        Person person = personService.findOne(id);
        if(person == null){
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }

        try {
            if(waitForAsyncResult){
                Future<Boolean> asyncResponse = emailService.sendAsyncWithResult(person);
                boolean emailSent = asyncResponse.get();
                logger.info("Person email sent? {}", emailSent);
            }else {
                emailService.sendAsync(person);
            }
        }catch (Exception e){
            logger.error("A problem occurred sending the Person.", e);
            return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Person>(person, HttpStatus.OK);

    }

}
