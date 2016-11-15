package com.semihbeceren.service;

import com.semihbeceren.model.Person;

import java.util.concurrent.Future;

/**
 * Created by Semih Beceren on 15.11.2016.
 */
public interface EmailService {

    Boolean send(Person person);

    void sendAsync(Person person);

    Future<Boolean> sendAsyncWithResult(Person person);

}
