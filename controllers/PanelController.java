package controllers;

import main.ParkeergarageSim;
import views.PanelView;
import views.StatisticsView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelController extends AbstractController {

    private PanelView panelView;
    private JButton advanceTime;
    private JButton stopSim;
    private JButton startSim;


    public PanelController() {
        addController(this);
        this.panelView = new PanelView(this);
        this.addStartAction();
        this.addStopAction();
        this.addResetTimeAction();
        this.addSliderAction();

        panelView.setBorder(new EmptyBorder(10,10,10,10));


    }

    public void addStartAction() {
        this.panelView.startSim.addActionListener((e) -> {
            ParkeergarageSim.setRunning(true);
        });
    }

    public void addStopAction() {
        this.panelView.stopSim.addActionListener((e) -> {
            ParkeergarageSim.setRunning(false);
        });
    }

    public void addResetTimeAction() {
        this.panelView.resetTime.addActionListener((e) -> {
            ParkeergarageSim.setTickPause(100);
            this.panelView.speedUp.setValue(100);

        });
    }

    public void addSliderAction() {
        this.panelView.speedUp.addChangeListener((e) -> {
            JSlider source = (JSlider)e.getSource();
            if(!source.getValueIsAdjusting()) {
                int speed = (int)source.getValue();

                    if (ParkeergarageSim.isRunning()) {
                        ParkeergarageSim.setTickPause(speed);
                    }

            }
        });
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateController() {

    }

    public PanelView getPanelView() {
        return this.panelView;
    }
}
