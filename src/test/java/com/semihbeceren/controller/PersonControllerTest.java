package com.semihbeceren.controller;

import com.semihbeceren.AbstractControllerTest;
import com.semihbeceren.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Semih Beceren on 15.11.2016.
 */
@Transactional
public class PersonControllerTest extends AbstractControllerTest{

    @Autowired
    PersonService personService;

    @Before
    @Override
    public void setUp(){
        super.setUp();
        personService.evictAllCaches();
    }

    @Test
    public void testPersons() throws Exception {
        String uri = "/api/persons";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();

        int status = result.getResponse().getStatus();

        Assert.assertEquals("fail - expected HTTP status 200", 200, status);
        Assert.assertTrue("fail - expected HTTP response body to have a value", content.trim().length() > 0);
    }
}
