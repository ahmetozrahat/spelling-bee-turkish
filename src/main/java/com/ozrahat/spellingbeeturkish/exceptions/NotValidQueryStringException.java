package com.ozrahat.spellingbeeturkish.exceptions;

public class NotValidQueryStringException extends Exception {

    public NotValidQueryStringException() {

    }

    public NotValidQueryStringException(String errorMessage) {
        super(errorMessage);
    }
}
