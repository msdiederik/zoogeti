package com.sogeti.zoogeti.feedingapp.exception;

public class CaretakerAlreadyBookedException extends RuntimeException {
    public CaretakerAlreadyBookedException() {
        super("This caretaker is already booked");
    }
}
