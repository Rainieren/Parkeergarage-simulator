package models;

import java.util.Random;
import java.awt.*;

public class Reserved extends Car {
    private static final Color COLOR = Color.yellow;
    private static final int typeOfCar = 3;

    private int resMinutes;
    private int stayMinutes;

    public Reserved() {
        Random random = new Random();
        stayMinutes = (int) (10 + random.nextFloat() * 3 * 60);
        resMinutes = random.nextInt(45) + 1;
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }

    public Color getColor() {

        return COLOR;
    }

    public int getTypeOfCar() {
        return typeOfCar;
    }

    public int getResMinutes(){ return resMinutes;}

    public int getStayMinutes() {return stayMinutes;}
}
