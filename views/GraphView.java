package views;

import controllers.GraphController;

import javax.swing.*;

import controllers.SimulatorController;
import controllers.StatisticsController;
import javafx.scene.chart.Chart;
import models.BarChart_AWT;
import models.GraphModel;
import models.SimulatorModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.awt.*;

public class GraphView extends AbstractView {

    public Dimension size;
    private BarChart_AWT chart;
    private GraphController controller;
    private GraphModel model;
    private JButton showChart;
    private StatisticsController statisticsController;
    private int timer = 0;
    private Image legendaImage;


    public GraphView(GraphController controller) {
        addView(this);
        this.controller = controller;
        this.statisticsController = statisticsController;
        this.size = new Dimension(0,0);
        this.chart = new BarChart_AWT("Charts", "Money earned", this.controller);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateView() {
        this.chart.getDataset().setValue(this.controller.getDaStacckss(), this.chart.getNormalCars(), this.chart.getAmountParked());
        this.chart.getPieDataset().setValue( this.chart.getNormalCars(), this.controller.getNumberOfCarsParked());
        this.chart.getPieDataset().setValue(this.chart.getPassCars(), this.controller.getNumberOfPassCarsParked());
        this.chart.getPieDataset().setValue(this.chart.getResCars(), this.controller.getNumberOfReservedPlaces());
        this.chart.getPieDataset().setValue(this.chart.getOpenSpaces(), this.controller.getNumberOfOpenSport());

        if (!this.size.equals(this.getSize())) {
            this.size = this.getSize();
            this.setLegendaImage(this.createImage(this.size.width, this.size.height));
        }

        Graphics graphics = this.getLegendaImage().getGraphics();
        this.drawPlace(graphics, 0, 0, Color.YELLOW, "Reserverings plek");
        this.drawPlace(graphics, 0, 1, Color.BLUE, "Abbonement auto");
        this.drawPlace(graphics, 0, 2, Color.RED, "Normale auto");
        this.drawPlace(graphics, 0, 3, Color.white, "Lege plek");
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (legendaImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(legendaImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(legendaImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    public void drawPlace(Graphics graphics, int x, int y, Color color, String text) {
        graphics.setColor(color);
        graphics.fillRect(x + 5, 60 + (y * 22) + 10, 38, 22);
        graphics.setColor(Color.black);
        graphics.drawString("  " + text, x + 40 + 5, 60 + (y * 22) + 10 + 14);
    }

    public Image getLegendaImage() {
        return this.legendaImage;
    }

    public void setLegendaImage(Image image) {
        this.legendaImage = image;
    }



}
