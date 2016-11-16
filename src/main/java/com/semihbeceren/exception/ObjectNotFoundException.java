package com.semihbeceren.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Semih Beceren on 16.11.2016.
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Object Not Found")
public class ObjectNotFoundException extends RuntimeException {

}