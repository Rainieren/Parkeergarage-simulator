package controllers;

import models.BarChart_AWT;
import models.GraphModel;
import models.SimulatorModel;
import models.StatisticsModel;
import org.jfree.data.category.CategoryRangeInfo;
import org.jfree.data.category.DefaultCategoryDataset;
import views.GraphView;
import org.jfree.data.category.CategoryDataset;

import javax.swing.border.EmptyBorder;

public class GraphController extends AbstractController {

    private GraphView graphView;
    private StatisticsController statisticsController;
    private StatisticsModel statisticsModel;
    private GraphModel graphModel;
    private SimulatorModel simulatorModel;
    private BarChart_AWT barChart_awt;

    public GraphController(StatisticsModel statisticsModel, GraphModel graphModel, SimulatorModel simulatorModel) {

        addController(this);
        graphView = new GraphView(this);
        this.statisticsModel = statisticsModel;
        this.graphModel = graphModel;
        this.simulatorModel = simulatorModel;
        this.barChart_awt = barChart_awt;
        graphView.setBorder(new EmptyBorder(10,10,10,10));

    }

    @Override
    public void tick() {

    }

    @Override
    public void updateController() {
    }

    public GraphView getGraphView() {
        return this.graphView;
    }

    public int getNumberOfPassCarsParked() { return this.simulatorModel.getNumberOfPassCarsParked(); }

    public int getNumberOfReservedPlaces() { return this.simulatorModel.getNumberReservedPlaces(); }

    public int getNumberOfOpenSport() { return this.simulatorModel.getNumberOfOpenSpots(); }

    public int getNumberOfCarsParked() { return this.simulatorModel.getNumberOfNormalParkedCars(); }

    public int getDaStacckss() { return this.simulatorModel.getRevenue(); }

    public int getDayIncome() { return this.simulatorModel.getDayIncome(); }
}
