package com.semihbeceren.controller;

import com.semihbeceren.AbstractControllerTest;
import com.semihbeceren.model.Person;
import com.semihbeceren.service.EmailService;
import com.semihbeceren.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Semih Beceren on 15.11.2016.
 */
@Transactional
public class PersonControllerMocks extends AbstractControllerTest {

    @Mock
    private PersonService personService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private PersonController personController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        super.setUp(personController);
    }


    @Test
    public void testPersons() throws Exception {

        Collection<Person> persons = getEntityListStubData();

        when(personService.findAll()).thenReturn(persons);

        String uri = "/api/persons";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();

        int status = result.getResponse().getStatus();

        verify(personService, times(1)).findAll();

        Assert.assertEquals("fail - expected HTTP status 200", 200, status);
        Assert.assertTrue("fail - expected HTTP response body to have a value", content.trim().length() > 0);
    }

    private Collection<Person> getEntityListStubData() {
        Collection<Person> persons = new ArrayList<>();
        Person person = new Person();
        person.setId(1L);
        person.setName("Semih");
        person.setSurname("Beceren");
        persons.add(person);
        return persons;
    }

}
