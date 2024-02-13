package com.example.hw02;

import java.io.Serializable;
import java.util.Date;

public class Drink implements Serializable {
    private int alcohol_percentage, drink_size;
    private Date addedOn;

    public Drink(int alcohol_percentage, int drink_size) {
        this.alcohol_percentage = alcohol_percentage;
        this.drink_size = drink_size;
        this.addedOn = new Date();
    }

    public Drink() {
        this.addedOn = new Date();
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public int getAlcohol_percentage() {
        return alcohol_percentage;
    }

    public void setAlcohol_percentage(int alcohol_percentage) {
        this.alcohol_percentage = alcohol_percentage;
    }

    public int getDrink_size() {
        return drink_size;
    }

    public void setDrink_size(int drink_size) {
        this.drink_size = drink_size;
    }
}
