package controllers;

import models.SimulatorModel;
import models.StatisticsModel;
import views.StatisticsView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class StatisticsController extends AbstractController {

    private StatisticsView view;
    private SimulatorModel simulatorModel;
    private StatisticsModel statisticsModel;

    public StatisticsController(SimulatorModel simulatorModel, StatisticsModel statisticsModel) {
        addController(this);
        this.simulatorModel = simulatorModel;
        this.statisticsModel = statisticsModel;
        view = new StatisticsView(this);

        view.setBorder(new EmptyBorder(10,10,10,10));
    }

    public StatisticsView getStatisticsView() {
        return view;
    }

    public int getRevenue() {
        return this.simulatorModel.getRevenue();
    }
//    public BigDecimal getRevenue() {
//        return this.simulatorModel.getRevenue();
//    }

    public int getAmountOfParkedCars() { return this.simulatorModel.totalNumberOfSpotsTaken(); }

    public int getNumberOfPassCarsParked() { return this.simulatorModel.getNumberOfPassCarsParked(); }

    public BigDecimal getAverageTime() { return this.simulatorModel.getAverageTime(); }

    public int getNumberOfReservedPlaces() { return this.simulatorModel.getNumberReservedPlaces(); }

    public int getNumberOfOpenSlots() { return this.simulatorModel.getNumberOfOpenSpots(); }

    public int getWeek() { return this.simulatorModel.getWeek(); }

    public BigDecimal getAverageParkingPrice() { return this.simulatorModel.getAverageParkingPrice(); }

    public int getTotalCarsLeft() { return this.simulatorModel.getNumberOfCarsLeft(); }

    public String getDate() { return this.simulatorModel.getDate(); }

    public int[] getWeeklyRevenue() { return this.simulatorModel.getWeeklyRevenue(); }

    public ArrayList<Integer> getWeekIncomes() { return this.simulatorModel.getWeekIncomes(); }

    public int getNormalCarsInQueue() { return this.simulatorModel.getNumberNormalCueuedCars(); }

    public int getPassCarsParked() { return this.simulatorModel.getNumberOfPassCarsParked(); }

    @Override
    public void tick() {

    }

    @Override
    public void updateController() {

    }
}
