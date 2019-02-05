package controllers;

import models.*;
import views.CarParkView;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;

public class SimulatorController extends AbstractController {
    private CarParkView carParkView;
    private SimulatorModel simulatorModel;
    private StatisticsModel statisticsModel;

    public SimulatorController(SimulatorModel simulatorModel, StatisticsModel statisticsModel) {
        addController(this);
        this.carParkView = new CarParkView(this);
        this.simulatorModel = simulatorModel;
        this.statisticsModel = statisticsModel;

    }

    public CarParkView getCarParkView() {
        return this.carParkView;
    }

    public int getRevenue() {
        return  this.simulatorModel.getRevenue();
    }
//    public BigDecimal getRevenue() {
//        return this.simulatorModel.getRevenue();
//    }

    public void updateView() {
        this.carParkView.updateView();

    }


    @Override
    public void tick() {
        this.simulatorModel.handleExit();
        this.simulatorModel.arrivalVariation();
        this.simulatorModel.addWeekIncome();
    }


    public Car getCarAt(Location location) {
       return this.simulatorModel.getCarAt(location);
    }

    @Override
    public void updateController() {
        this.simulatorModel.handleEntrance();
    }

    public int getNumberOfFloors() {
        return this.simulatorModel.getNumberOfFloors();
    }

    public int getNumberOfRows() {
        return this.simulatorModel.getNumberOfRows();
    }

    public int getNumberOfPlaces() {
        return this.simulatorModel.getNumberOfPlaces();
    }

    public int getNumberOfPassCarsParked() { return this.simulatorModel.getNumberOfPassCarsParked(); }
}
