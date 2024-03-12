package com.sogeti.zoogeti.feedingapp.exception;

public class NotEnoughFoodLeftException extends RuntimeException {
    public NotEnoughFoodLeftException(String food, String species) {
        super("Not enough "+food+" left to feed " + species);
    }
}
