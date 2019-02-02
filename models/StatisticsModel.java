package models;

import controllers.StatisticsController;

public class StatisticsModel {

    public int revenue = 0;

    public StatisticsModel() {

    }

    public void addRevenue(float value) {
        revenue += value;
    }
}
