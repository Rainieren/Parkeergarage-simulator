package models;

import controllers.SimulatorController;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class SimulatorModel {

    public static final String AD_HOC = "1";
    public static final String PASS = "2";
    public static final String RES = "3";
    public float revenue;
    private float averageParkingPrice;
    private ArrayList<Float> times = new ArrayList<Float>();
    private int[] weeklyRevenue;
    private int currentDay = 0;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private int numberOfParkedCars;
    private int numberOfPassCarsParked;
    private int numberReservedPlaces;
    private int numberOfCarsLeft;
    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private Car[][][] cars;


    public SimulatorModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        this.cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        this.weeklyRevenue = new int[7];
    }

    public void handleEntrance(){
        carsArriving();
        carsEntering(entrancePassQueue, PASS);
        carsEntering(entranceCarQueue, AD_HOC);
    }

    public void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }



    public static void arrivalVariation() {
        // schedules.
        if (Time.getDay() >= 0 && Time.getDay() <= 4){
            // schedule for tuesdays, every week it is "koop avond" that day. therefor it get's an exception.
            if(Time.getDay() == 3){
                switch (Time.getHour()) {
                    case 8:
                        Car.setWeekDayArrivals(300);
                        Car.setWeekDayPassArrivals(300);
                        break;
                    case 9:
                        Car.setWeekDayArrivals(Car.getWeekDayArrivals()/2);
                        Car.setWeekDayPassArrivals(Car.getWeekDayPassArrivals()/2);
                        break;
                    case 12:
                        Car.setWeekDayArrivals(Car.getWeekDayArrivals()/2);
                        Car.setWeekDayPassArrivals(Car.getWeekDayPassArrivals()/2);
                        break;
                    case 13:
                        Car.setWeekDayArrivals(Car.getWeekDayArrivals()*2);
                        Car.setWeekDayPassArrivals(Car.getWeekDayPassArrivals()*2);
                        break;
                    case 17:
                        Car.setWeekDayArrivals(Car.getWeekDayArrivals()/2);
                        Car.setWeekDayPassArrivals(Car.getWeekDayPassArrivals()/2);
                        break;
                    //'koopavond' below.
                    case 21:
                        Car.setWeekDayArrivals(Car.getWeekDayArrivals()/2);
                        Car.setWeekDayPassArrivals(Car.getWeekDayPassArrivals()/2);
                        break;
                    case 22:
                        Car.setWeekDayArrivals(0);
                        Car.setWeekDayPassArrivals(0);
                        break;
                }
            }
            // schedule for week days.
            switch (Time.getHour()) {
                case 8:
                    Car.setWeekDayArrivals(200);
                    Car.setWeekDayPassArrivals(300);
                    break;
                case 9:
                    Car.setWeekDayArrivals(Car.getWeekDayArrivals()/2);
                    Car.setWeekDayPassArrivals(Car.getWeekDayPassArrivals()/2);
                    break;
                case 12:
                    Car.setWeekDayArrivals(Car.getWeekDayArrivals()/2);
                    Car.setWeekDayPassArrivals(Car.getWeekDayPassArrivals()/2);
                    break;
                case 13:
                    Car.setWeekDayArrivals(Car.getWeekDayArrivals()*2);
                    Car.setWeekDayPassArrivals(Car.getWeekDayPassArrivals()*2);
                    break;
                case 17:
                    Car.setWeekDayArrivals(Car.getWeekDayArrivals()/2);
                    Car.setWeekDayPassArrivals(Car.getWeekDayPassArrivals()/2);
                    break;
                case 22:
                    Car.setWeekDayArrivals(0);
                    Car.setWeekDayPassArrivals(0);
                    break;
            }
            //end of weekdays.
            //
            //schedule for saturday.
            if(Time.getDay() == 5){
                switch(Time.getHour()){
                    case 10:
                        Car.setWeekendArrivals(0);
                        Car.setWeekendPassArrivals(0);
                        break;
                    case 13:
                        Car.setWeekendArrivals(Car.getWeekendArrivals()*2);
                        Car.setWeekendPassArrivals(Car.getWeekendPassArrivals()*2);
                        break;
                    case 17:
                        Car.setWeekendArrivals(Car.getWeekendArrivals()/2);
                        Car.setWeekendPassArrivals(Car.getWeekendPassArrivals()/2);
                        break;
                    case 22:
                        Car.setWeekendArrivals(0);
                        Car.setWeekendPassArrivals(0);
                        break;
                }
            }
            //end of saturday schedule.
            //
            //schedule for sunday.
            if(Time.getDay() == 6){
                switch (Time.getHour()) {
                    case 8:
                        Car.setWeekendArrivals(10);
                        Car.setWeekendPassArrivals(2);
                        break;
                    case 12:
                        Car.setWeekendArrivals(50);
                        Car.setWeekendPassArrivals(5);
                        break;
                    case 17:
                        Car.setWeekendArrivals(Car.getWeekendArrivals()/2);
                        Car.setWeekendPassArrivals(Car.getWeekendPassArrivals()/2);
                        break;
                    case 22:
                        Car.setWeekendArrivals(0);
                        Car.setWeekendPassArrivals(0);
                        break;
                }
            }
        }
    }

    public static void eventMultiplier() {
        if(Event.eventCheck()) {
            Car.setMultiplier(2);
        }
        Car.setMultiplier(1);
    }

    private void carsEntering(CarQueue queue, String type){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 &&
                getNumberOfOpenSpots() > 0 &&
                i < Car.getExitSpeed()) {
            Car car = queue.removeCar();

            if(car.getTypeOfCar() == 2) {
                numberOfPassCarsParked++;
            }
            if (car.getTypeOfCar() == 3) {
                numberReservedPlaces++;
            }

            Location freeLocation = getFirstFreeLocation();
            setCarAt(freeLocation, car);
            numberOfParkedCars++;
            i++;
        }
    }

    private void carsLeaving(){
        // Let cars leave.
        int i=0;
        while (exitCarQueue.carsInQueue() > 0 && i < Car.getExitSpeed()){
            exitCarQueue.removeCar();
            i++;
        }
    }

    private void carsArriving(){
        int numberOfCars = getNumberOfCars(Car.getWeekDayArrivals(), Car.getWeekendArrivals());
        addArrivingCars(numberOfCars, AD_HOC);
        numberOfCars = getNumberOfCars(Car.getWeekDayPassArrivals(), Car.getWeekendPassArrivals());
        addArrivingCars(numberOfCars, PASS);
        numberOfCars = getNumberOfCars(Car.getWeekDayPassArrivals(), Car.getWeekendPassArrivals());
        addArrivingCars(numberOfCars, RES);
    }

    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = getFirstLeavingCar();
        while (car!=null) {
            if (car.getHasToPay()){
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            }
            else {
                carLeavesSpot(car);
            }
            car = getFirstLeavingCar();
        }
    }

    private float calcWeekyRevenue() {
        float total = 0.0f;

        for(int x = 0; x < weeklyRevenue.length; x++) {
            total += weeklyRevenue[x];
        }

        return total;
    }

    private void carsPaying() {
        // Let cars pay.
        float total = 0.0f;
        int i = 0;
        while (paymentCarQueue.carsInQueue() > 0 && i < Car.getPaymentSpeed()){
            Car car = paymentCarQueue.removeCar();

            if(currentDay != Time.getDay()) {
                weeklyRevenue[Time.getDay()] = 0;
                currentDay = Time.getDay();
            }
            // TODO Handle payment.
            float payment = car.getTotalMinutes() * 0.042f;

            weeklyRevenue[Time.getDay()] += payment;

            revenue += payment;

            carLeavesSpot(car);
            numberOfParkedCars--;
            numberOfCarsLeft++;

            float timeInFloat = car.getTotalMinutes();
            times.add(timeInFloat);

            i++;
        }

        System.out.println(total);

    }

    public BigDecimal getAverageTime() {

        float totalTime = 0.0f;

        for(Float time : times) {
            totalTime += time;
        }

        float average = totalTime / times.size();
        average = average / 60;
        int hour = (int) average;
        int minuut = (int) (60 * (average - hour));

        float temp1 = (float) minuut / 100;
        float temp2 = hour + temp1;

        return round(temp2, 2);

    }

    public BigDecimal round(float value, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(value));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

//    public int getNormalCars() {
//
//    }

    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
        switch(type) {
            case AD_HOC:
                for (int i = 0; i < numberOfCars; i++) {
                    if(entranceCarQueue.carsInQueue() <= 30) {
                        entranceCarQueue.addCar(new AdHocCar());
                    }
                }
                break;
            case PASS:
                if(entrancePassQueue.carsInQueue()<=40) {
                    entrancePassQueue.addCar(new ParkingPassCar());
                }
                break;
            case RES:
                for(int i = 0; i < numberOfCars; i++) {
                    if(entrancePassQueue.carsInQueue()<=40) {
                        entrancePassQueue.addCar(new Reserved());
                    }
                }
                break;
        }
    }

    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = Time.getDay() < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);
    }

    private void carLeavesSpot(Car car){
        removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }

    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            numberOfOpenSpots--;

            if(car.getColor().equals(Color.blue)) {
                numberReservedPlaces++;
            }

            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        car.setLocation(null);



        numberOfOpenSpots++;
        return car;
    }

    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }



    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public ArrayList<Float> getTimes() {
        return times;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public int getWeek() {
        return Time.getWeek();
    }

    public int getNumberOfOpenSpots() {
        return numberOfOpenSpots;
    }

    public int getNumberOfCarsLeft() {
        return numberOfCarsLeft;
    }

    public BigDecimal getAverageParkingPrice() {
        float round = getAverageTime().setScale(2, RoundingMode.DOWN).floatValue();
        float result = round * 60 * 0.042f;
        return round(result, 2);
    }

    public BigDecimal getRevenue() {
        return round(revenue, 2);
    }

    public int getNumberReservedPlaces() {
        return numberReservedPlaces;
    }

    public String getDate() {
        return Time.getDate();
    }

    public int totalNumberOfSpotsTaken() {
        int amount = numberOfRows * numberOfFloors * numberOfPlaces;
        return amount - numberOfOpenSpots;
    }

    public int[] getWeeklyRevenue() {
        return weeklyRevenue;
    }

    public int getNumberOfParkedCars() {
        return numberOfParkedCars;
    }

    public int getNumberOfPassCarsParked() {
        return numberOfPassCarsParked;
    }

    public int getNumberNormalCueuedCars() {
        return entranceCarQueue.carsInQueue();
    }

    public Car[][][] getCars() {
        return cars;
    }

}
