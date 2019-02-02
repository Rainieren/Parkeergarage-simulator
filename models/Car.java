package models;

import java.awt.*;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    private int totalMinutes;
    private static int multiplier = 1;
    private static int enterSpeed = 3; // number of cars that can enter per minute
    private static int paymentSpeed = 7; // number of cars that can pay per minute
    private static int exitSpeed = 5; // number of cars that can leave per minute
    private static int weekDayArrivals = 100 * multiplier; // average number of arriving cars per hour
    private static int weekendArrivals = 200 * multiplier; // average number of arriving cars per hour
    private static int weekDayPassArrivals = 50 * multiplier; // average number of arriving cars per hour
    private static int weekendPassArrivals = 5 * multiplier; // average number of arriving cars per hour


    /**
     * Constructor for objects of class models.Car
     */
    public Car() {

    }

    public static int getWeekDayArrivals() {
        return weekDayArrivals;
    }

    public static void setWeekDayArrivals(int weekDayArrivals) {
        Car.weekDayArrivals = weekDayArrivals;
    }

    public static void setWeekendArrivals(int weekendArrivals) {
        Car.weekendArrivals = weekendArrivals;
    }

    public static void setWeekDayPassArrivals(int weekDayPassArrivals) {
        Car.weekDayPassArrivals = weekDayPassArrivals;
    }

    public static void setWeekendPassArrivals(int weekendPassArrivals) {
        Car.weekendPassArrivals = weekendPassArrivals;
    }


    public static int getWeekendArrivals() {
        return weekendArrivals;
    }

    public static int getWeekDayPassArrivals() {
        return weekDayPassArrivals;
    }

    public static int getWeekendPassArrivals() {
        return weekendPassArrivals;
    }

    public static void setMultiplier(int value) {
        multiplier = value;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public int getTotalMinutes() {
        return totalMinutes;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
        totalMinutes = minutesLeft;
    }
    
    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }

    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();
    public static int getEnterSpeed() {
        return enterSpeed;
    }

    public abstract int getTypeOfCar();

    public static int getPaymentSpeed() {
        return paymentSpeed;
    }

    public static int getExitSpeed() {
        return exitSpeed;
    }

}