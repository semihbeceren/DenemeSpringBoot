package com.semihbeceren.service;

import com.semihbeceren.AbstractTest;
import com.semihbeceren.model.Person;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Collection;

/**
 * Created by Semih Beceren on 15.11.2016.
 */
@Transactional //Test işlemlerinin geri döndürülebilmesi için
public class PersonServiceTest extends AbstractTest {

    @Autowired
    PersonService personService;

    @Before
    public void setUp(){
        personService.evictAllCaches();
    }

    @After
    public void tearDown(){
        //clean up
    }

    @Test
    public void testFindAll(){
        Collection<Person> persons = personService.findAll();
        Assert.assertNotNull("fail - expected not null", persons);
        Assert.assertEquals("fail - expected size", 2, persons.size());
    }

    @Test
    public void testFindOne(){
        Long id = 1L;
        String expectedName = "Semih", expectedSurname = "Beceren";
        Person person = personService.findOne(id);

        Assert.assertNotNull("fail - expected not null", person);
        Assert.assertEquals("fail - expected id attribute match", id, person.getId());
        Assert.assertEquals("fail - expected name attribute match", expectedName, person.getName());
        Assert.assertEquals("fail - expected surname attribute match", expectedSurname, person.getSurname());
    }

    @Test
    public void testFindOneNotFound(){
        Long id = Long.MAX_VALUE;
        Person person = personService.findOne(id);

        Assert.assertNull("fail - expected null", person);
    }

    @Test
    public void createWithId(){
        Long id = Long.MAX_VALUE;

        Exception ex = null;

        Person person = new Person();
        person.setId(id);
        person.setName("Ali");
        person.setSurname("Karaman");
        try {
            personService.create(person);
        }catch (SpelEvaluationException e){
            ex = e;
        }

        Assert.assertNotNull("fail - expected exception", ex);
        Assert.assertTrue("fail - expected SpelEvaluationException", ex instanceof SpelEvaluationException);
    }

}
