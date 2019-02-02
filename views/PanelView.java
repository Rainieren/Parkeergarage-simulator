package views;

import controllers.PanelController;
import main.ParkeergarageSim;
import models.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class PanelView extends AbstractView {

    public Dimension size;
    public JButton fastForwardTime;
    public JButton slowDownTime;
    public JButton resetTime;
    public JButton stopSim;
    public JButton startSim;
    public JSlider speedUp;


    private PanelController controller;

    public PanelView(PanelController controller) {
        addView(this);

        this.controller = controller;
        this.resetTime = new JButton("Reset Time");
        this.startSim = new JButton("Start Simulation");
        this.stopSim = new JButton("Stop Simulation");
        this.size = new Dimension(0, 0);
        this.speedUp = new JSlider(JSlider.HORIZONTAL, 25, 175, 100);

        Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();

        labels.put(25, new JLabel("Fast"));
        labels.put(175, new JLabel("Slow"));

        this.speedUp.setLabelTable(labels);
        this.speedUp.setPaintLabels(true);
        this.speedUp.setPaintTicks(true);

//        add(fastForwardTime);
//        add(slowDownTime);
        add(this.speedUp);
        add(resetTime);
        add(stopSim);
        add(startSim);


    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 100);
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateView() {

    }
}
