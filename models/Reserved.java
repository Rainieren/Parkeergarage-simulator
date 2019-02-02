package models;

import java.util.Random;
import java.awt.*;

public class Reserved extends Car {
    private static final Color COLOR = Color.yellow;
    private static final int typeOfCar = 3;

    public Reserved() {
        Random random = new Random();
        int stayMinutes = (int) (10 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public Color getColor() {

        return COLOR;
    }

    public int getTypeOfCar() {
        return typeOfCar;
    }
}
