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
    public int revenue;
//    public float revenue;
    private float averageParkingPrice;
    private ArrayList<Float> times = new ArrayList<Float>();
    private ArrayList<Integer> weekIncomes = new ArrayList<Integer>();
    private int[] weeklyRevenue;
    private int dayIncome;
    private int currentDay = 0;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private int numberOfOpenSpots;
    private int numberOfParkedCars;
    private int numberOfNormalParkedCars;
    private int numberOfPassCarsParked;
    private int numberReservedPlaces;
    private int numberOfCarsLeft;

    //

    private int numberOfVipRows;
    private int numberOfVipFloors;

    private int maxPassCar;
    private int numberPassCar;

    private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    // private CarQueue entranceResQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private Car[][][] cars;


    public SimulatorModel(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        // entranceRessQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots = numberOfFloors * numberOfRows * numberOfPlaces;
        this.cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        this.weeklyRevenue = new int[7];

        // RESERVATION
        // this.numberOfVipRows = numbersOfVipRows;
        // this.numberOfVipFloors = numberOfVipFloors;

        // this.maxPassCar = numberOfVipFloors * numberOfVipRows * numberOfPlaces;
        // this.numberPassCar = 0;
    }

    public void handleEntrance(){
        carsArriving();
        carsEntering(entrancePassQueue, PASS);
        carsEntering(entranceCarQueue, AD_HOC);
        // carsEntering(entranceResQueue, RES);

    }

    public void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }

    public void setWeekdayArrivals(int amountOfNormalCars, int amountOfPassCars, int amountOfReservations) {
        Car.setWeekDayArrivals(amountOfNormalCars);
        Car.setWeekDayPassArrivals(amountOfPassCars);
        Car.setWeekDayReservation(amountOfReservations);
    }

    public void setWeekendArrivals(int amountOfNormalCars, int amountOfPassCars, int amountOfReservations) {
        Car.setWeekendArrivals(amountOfNormalCars);
        Car.setWeekendPassArrivals(amountOfPassCars);
        Car.setWeekendReservation(amountOfReservations);
    }

    public void arrivalVariation() {
        // schedules.

        if(Time.getDay() >= 0 && Time.getDay() <= 4) {
            if(Time.getDayString().equals("Donderdag")) {
                switch (Time.getHour()) {
                    case 6:
                        setWeekdayArrivals(35, 20, 25);
                        break;
                    case 7:
                        setWeekdayArrivals(135, 50, 30);
                        break;
                    case 8:
                        setWeekdayArrivals(185,75,50);
                        break;
                    case 12:
                        setWeekdayArrivals(200,75,50);
                        break;
                    case 19:
                        setWeekdayArrivals(100, 35,15);
                        break;
                    case 20:
                        setWeekdayArrivals(50, 15, 10);
                        break;
                    case 21:
                        setWeekdayArrivals(25, 10,10);
                        break;
                    case 0:
                        setWeekdayArrivals(18,10,0);
                }
            } else {
                switch (Time.getHour()) {
                    case 6:
                        setWeekdayArrivals(35, 20, 25);
                        break;
                    case 7:
                        setWeekdayArrivals(125, 50, 30);
                        break;
                    case 8:
                        setWeekdayArrivals(175,75,50);
                        break;
                    case 12:
                        setWeekdayArrivals(85,55,30);
                        break;
                    case 13:
                        setWeekdayArrivals(150, 65,35);
                        break;
                    case 17:
                        setWeekdayArrivals(45, 35, 25);
                        break;
                    case 21:
                        setWeekdayArrivals(25, 25,0);
                        break;
                    case 0:
                        setWeekdayArrivals(18,15,0);
                        break;
                }
            }
        }

        if(Time.getDayString().equals("Zaterdag")) {
            switch (Time.getHour()) {
                case 6:
                    setWeekendArrivals(35, 20, 25);
                    break;
                case 7:
                    setWeekendArrivals(75, 45, 35);
                    break;
                case 8:
                    setWeekendArrivals(125,55,35);
                    break;
                case 12:
                    setWeekendArrivals(85,55,30);
                    break;
                case 13:
                    setWeekendArrivals(125, 55,35);
                    break;
                case 17:
                    setWeekendArrivals(35, 20, 20);
                    break;
                case 21:
                    setWeekendArrivals(25, 25,0);
                    break;
                case 0:
                    setWeekendArrivals(18,15,0);
                    break;
            }
        }

        if(Time.getDayString().equals("Zondag")) {
            switch (Time.getHour()) {
                case 6:
                    setWeekendArrivals(25, 20, 15);
                    break;
                case 7:
                    setWeekendArrivals(35, 25, 25);
                    break;
                case 8:
                    setWeekendArrivals(55,35,25);
                    break;
                case 17:
                    setWeekendArrivals(35, 20, 20);
                    break;
                case 21:
                    setWeekendArrivals(25, 25,0);
                    break;
                case 0:
                    setWeekendArrivals(18,15,0);
                    break;
            }
        }
    }



    private void carsEntering(CarQueue queue, String type){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
        while (queue.carsInQueue() > 0 &&
                getNumberOfOpenSpots() > 0 &&
                i < Car.getExitSpeed()) {
            Car car = queue.removeCar();

            switch(car.getTypeOfCar()) {
                case 1:
                    numberOfNormalParkedCars++;
                    break;
                case 2:
                    numberOfPassCarsParked++;
                    break;
                case 3:
                    numberReservedPlaces++;
                    break;
            }

            Location freeLocation = getFirstFreeLocation();
            setCarAt(freeLocation, car);
            numberOfParkedCars++;

            // RESERVATION
            /*
            Location freeLocation;
            switch(type) {
                case PASS:
                    //Antonie: Dont add another passCar if all the passCars are on the parking lot!
                    if(numberPassCar < maxPassCar) {
                        //A pass car has come to the parking lot
                        numberPassCar++;
                        freeLocation = getFirstFreeVipLocation();
                        setCarAt(freeLocation, car);
                    }
                        break;
                case RES:
                    freeLocation = getFirstFreeLocation();
                    setCarAt(freeLocation, car);
                    break;
                default:
                    freeLocation = getFirstFreeLocation();
                    setCarAt(freeLocation, car);
                    break;
            }

             */

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
        numberOfCars = getNumberOfCars(Car.getWeekDayReservation(), Car.getWeekendReservation());
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
                // numberPassCar--;
                if(car.getTypeOfCar() == 2) {
                    numberOfPassCarsParked--;
                }
                carLeavesSpot(car);
            }
            car = getFirstLeavingCar();
        }
    }

    private void carsPaying() {
        // Let cars pay.
        int amount = 0;
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

            switch(car.getTypeOfCar()) {
                case 1:
                    numberOfNormalParkedCars--;
                    break;
                case 3:
                    numberReservedPlaces--;
                    break;
            }

            carLeavesSpot(car);
            numberOfParkedCars--;
            numberOfCarsLeft++;

            float timeInFloat = car.getTotalMinutes();
            times.add(timeInFloat);

            i++;
        }

    }

    public void addWeekIncome() {
        if(Time.isEndOfWeek()) {
            int total = 0;
            for(int money : weeklyRevenue) {
                total += money;
            }
            weekIncomes.add(total);
//            revenue += total;
        }
    }

    public void addDayIncome() {
        if(currentDay == Time.getDay() && Time.getHour() == 23 && Time.getMinute() == 59) {
            dayIncome += weeklyRevenue[Time.getDay()];
            revenue += weeklyRevenue[Time.getDay()];
        }
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
                for(int i = 0; i < numberOfCars; i++) {
                    if (entrancePassQueue.carsInQueue() <= 40) {
                        entrancePassQueue.addCar(new ParkingPassCar());
                    }
                }
                break;
            case RES:
                for(int i = 0; i < numberOfCars; i++) {
                    if(entrancePassQueue.carsInQueue() <= 40) {
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

    // RESERVATIE
//    public boolean locationIsVip(Location location) {
//        int row = location.getRow();
//        int floor = location.getFloor();
//        if(row < numberOfVipRows && floor < numberOfVipFloors) {
//            return true;
//        }
//
//        return false;
//    }

    public Car getCarAt(Location location) {
        if(location == null) {
            return null;
        }
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

    // RESERVATIE
//    public Location getFirstFreeVipLocation() {
//        for (int floor = 0; floor < getNumberOfVipFloors(); floor++) {
//            for (int row = 0; row < getNumberOfVipRows(); row++) {
//                for (int place = 0; place < getNumberOfPlaces(); place++) {
//                    Location location = new Location(floor, row, place);
//                    if (getCarAt(location) == null && locationIsVip(location)) {
//                        return location;
//                    }
//                }
//            }
//        }
//
//        Location location = getFirstFreeLocation();
//        return location;
//    }

    // RESERVATIE
//    public void checkForReservation() {
//        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
//            for (int row = 0; row < getNumberOfRows(); row++) {
//                for (int place = 0; place < getNumberOfPlaces(); place++) {
//                    Location location = new Location(floor, row, place);
//                    Car car = getCarAt(location);
//                    if (car != null && car.getColor() == Color.gray && (car.getStayMinutes() - car.getResMinutes()) > car.getMinutesLeft()) {
//                        car.changeColor();
//                    }
//                }
//            }
//        }
//    }

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


    public int totalNumberOfSpotsTaken() {
        int amount = numberOfRows * numberOfFloors * numberOfPlaces;
        return amount - numberOfOpenSpots;
    }

    public ArrayList<Integer> getWeekIncomes() {

        return weekIncomes;
    }

    public BigDecimal getAverageParkingPrice() {
        float round = getAverageTime().setScale(2, RoundingMode.DOWN).floatValue();
        float result = round * 60 * 0.042f;
        return round(result, 2);
    }


    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public ArrayList<Float> getTimes() {
        return times;
    }
    // RESERVATIE

//    public int getNumberOfVipRows() {
//        return numberOfVipRows;
//    }
//
//    public int getNumberOfVipFloors() {
//        return numberOfVipFloors;
//    }

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

    public int getRevenue() { return revenue; }

    public int getDayIncome() { return dayIncome; }

    public int getNumberReservedPlaces() {
        return numberReservedPlaces;
    }

    public String getDate() {
        return Time.getDate();
    }

    public int[] getWeeklyRevenue() {
        return weeklyRevenue;
    }

    public int getNumberOfNormalParkedCars() { return numberOfNormalParkedCars; }

    public int getNumberOfParkedCars() {
        return numberOfParkedCars;
    }

    public int getNumberOfPassCarsParked() {
        return numberOfPassCarsParked;
    }

    public int getNumberNormalCueuedCars() {
        return entranceCarQueue.carsInQueue();
    }
    // RESERVATIE

//    public CarQueue getEntranceCarQueue(){
//        return entranceCarQueue;
//    }
//
//
//    public CarQueue getEntrancePassQueue() {
//        return entrancePassQueue;
//    }
//
//    public CarQueue getEntranceResQueue() {
//        return entranceResQueue;
//    }

    public Car[][][] getCars() {
        return cars;
    }

}
