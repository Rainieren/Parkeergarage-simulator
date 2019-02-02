package controllers;

import models.GraphModel;
import models.SimulatorModel;
import views.GraphView;

import javax.swing.border.EmptyBorder;

public class GraphController extends AbstractController {

    private GraphView graphView;
    private SimulatorModel simulatorModel;
    private GraphModel graphModel;

    public GraphController(SimulatorModel simulatorModel) {

        addController(this);
        this.graphView = new GraphView(this);
        this.simulatorModel = simulatorModel;


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
}
