package views;

import controllers.GraphController;

import javax.swing.*;
import java.awt.*;

public class GraphView extends AbstractView {

    public Dimension size;

    private GraphController controller;


    public GraphView(GraphController controller) {
        addView(this);
        this.controller = controller;
        this.size = new Dimension(200,200);
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateView() {

    }


}
