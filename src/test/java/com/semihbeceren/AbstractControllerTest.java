package com.semihbeceren;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semihbeceren.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

/**
 * Created by Semih Beceren on 15.11.2016.
 */
@WebAppConfiguration
public class AbstractControllerTest extends AbstractTest {
    protected MockMvc mvc;

    @Autowired
    protected WebApplicationContext applicationContext;

    protected void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    protected void setUp(BaseController controller){
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    protected String mapToJSON(Object obj)throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJSON(String JSON, Class<T> clazz)throws JsonParseException, JsonMappingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(JSON, clazz);
    }

}
