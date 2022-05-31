package com.ozrahat.spellingbeeturkish.exceptions;

public class NoSuchWordListException extends Exception {

    public NoSuchWordListException() {

    }

    public NoSuchWordListException(String errorMessage) {
        super(errorMessage);
    }
}
