package com.sogeti.zoogeti.feedingapp.exception;

public class FoodNotAllowedException extends RuntimeException {
    public FoodNotAllowedException(String food, String species) {
        super(species + " is not allowed to have " + food);
    }
}
