package com.semihbeceren.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Semih Beceren on 16.11.2016.
 */
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Object not found for update statement.")
public class ObjectNotFoundForUpdateException extends RuntimeException {

}