package com.mananger_veterinary.vetman.domain.exception;

public class DuplicatePetException extends RuntimeException {

    public DuplicatePetException(String message) {
        super(message);
    }
}