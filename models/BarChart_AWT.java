package models;

import controllers.GraphController;
import controllers.SimulatorController;
import controllers.StatisticsController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

public class BarChart_AWT extends ApplicationFrame {

    private GraphController graphController;
    public int AmountNormalCars = 1;
    private DefaultCategoryDataset dataset;
    private DefaultPieDataset pieDataset;

    public String getNormalCars() {
        return normalCars;
    }

    public String getPassCars() {
        return passCars;
    }

    public String getResCars() {
        return resCars;
    }

    public String getOpenSpaces() {
        return openSpaces;
    }

    public String getAmountParked() {
        return amountParked;
    }

    private final String normalCars = "Normal cars";
    private final String passCars = "Pass cars";
    private final String resCars = "Reservations";
    private final String openSpaces = "Open spaces";
    private final String amountParked = "Euro's";

    public BarChart_AWT(String applicationTitle, String chartTitle, GraphController graphController) {
        super(applicationTitle);
        this.graphController = graphController;
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Amount of money earned",
                "Amount",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        JFreeChart pie = ChartFactory.createPieChart(
                "Amount of cars",
                createPieDataset()
        );

        JPanel Kameelpaneel = new JPanel();

        CategoryPlot plot = barChart.getCategoryPlot();

        barChart.setBackgroundPaint(Color.white);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(430, 580));

        ChartPanel piePannel = new ChartPanel(pie);
        piePannel.setPreferredSize(new Dimension(430, 580));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        PiePlot plottie = (PiePlot) pie.getPlot();

        plottie.setSectionPaint(0, Color.red);
        plottie.setSectionPaint(1, Color.blue);
        plottie.setSectionPaint(2, Color.yellow);
        plottie.setSectionPaint(3, Color.white);
        renderer.setSeriesPaint(0, Color.ORANGE);
        renderer.setSeriesPaint(1, Color.red);


        renderer.setBarPainter(new StandardBarPainter());

        Kameelpaneel.add(chartPanel);
        Kameelpaneel.add(piePannel);
        setContentPane(Kameelpaneel);
//        setContentPane(piePannel);
    }

    public PieDataset createPieDataset() {
        pieDataset = new DefaultPieDataset ();

        pieDataset.setValue(normalCars, 0);
        pieDataset.setValue(passCars, 0);
        pieDataset.setValue(resCars, 0);
        pieDataset.setValue(openSpaces, 0);

        return pieDataset;
    }

    public CategoryDataset createDataset() {
        dataset = new DefaultCategoryDataset();

        dataset.addValue(0, normalCars, amountParked);

        return dataset;
    }

    public DefaultCategoryDataset getDataset() {
        return this.dataset;
    }

    public DefaultPieDataset getPieDataset() {
        return this.pieDataset;
    }


}
