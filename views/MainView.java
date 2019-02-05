package views;

import controllers.*;
import models.BarChart_AWT;
import models.GraphModel;
import models.SimulatorModel;
import models.StatisticsModel;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() throws HeadlessException {
        SimulatorModel simulatorModel = new SimulatorModel(3, 6, 30);
        StatisticsModel statisticsModel = new StatisticsModel();
        GraphModel graphModel = new GraphModel();
        SimulatorController simulatorController = new SimulatorController(simulatorModel, statisticsModel);
        StatisticsController statisticsController = new StatisticsController(simulatorModel, statisticsModel);
        PanelController panelController = new PanelController();
        GraphController graphController = new GraphController(statisticsModel, graphModel, simulatorModel);
        TimerController timerController = new TimerController();


        Container contentPane = getContentPane();

        contentPane.add(timerController.getTimerView(), BorderLayout.NORTH);
        contentPane.add(simulatorController.getCarParkView(), BorderLayout.CENTER);
        contentPane.add(statisticsController.getStatisticsView(), BorderLayout.WEST);
        contentPane.add(panelController.getPanelView(), BorderLayout.SOUTH);
        contentPane.add(graphController.getGraphView(), BorderLayout.EAST);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        simulatorController.updateView();
    }
}
