package com.semihbeceren.service;

import com.semihbeceren.model.Person;
import com.semihbeceren.util.AsyncResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * Created by Semih Beceren on 15.11.2016.
 */
@Service
public class EmailServiceImpl implements EmailService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public Boolean send(Person person) {
        logger.info("-> Send");
        Boolean success = Boolean.FALSE;
        simulateExecution(5000);
        success = Boolean.TRUE;
        logger.info("<- Send");
        return success;
    }

    @Async
    @Override
    public void sendAsync(Person person) {
        logger.info("-> Send Async");
        try {
            send(person);
        }catch (Exception e){
            logger.warn("Exception caught on sending async mail", e);
        }
        logger.info("<- Send Async");
    }

    @Override
    public Future<Boolean> sendAsyncWithResult(Person person) {
        logger.info("-> Send Async With Result");
        AsyncResponse<Boolean> response = new AsyncResponse<>();

        try {
            Boolean success = send(person);
            response.complete(success);
        }catch (Exception e){
            logger.warn("Exception caught on sending async mail", e);
            response.completeExceptionally(e);
        }

        logger.info("<- Send Async With Result");
        return response;
    }

    private void simulateExecution(long wait){
        try {
            Thread.sleep(wait);
            logger.info("Processing time was {} seconds.", wait/1000);
        }catch (Exception e){}
    }
}
