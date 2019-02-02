package views;

import controllers.StatisticsController;
import models.SimulatorModel;
import models.Time;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatisticsView extends AbstractView {

    public Image legendaImage;
    public Dimension size;
    public JLabel statsTitel;
    public JLabel week;
    public JLabel date;
    public JLabel revenue;
    public JLabel parkedCars;
    public JLabel parkedSubCars;
    public JLabel averageParkingTime;
    public JLabel amountOfReservations;
    public JLabel amountAvailablePlaces;
    public JLabel averageParkingPrice;
    public JLabel amountOfCarsLeft;
    public JLabel weekRevenueLabel;
    public JLabel normalCarsInQueue;

    private StatisticsController controller;

    public StatisticsView(StatisticsController controller) {
        addView(this);
        this.controller = controller;
        size = new Dimension(0,0);

        JPanel statsPanel = new JPanel();

        BoxLayout layoutBox = new BoxLayout(statsPanel, BoxLayout.Y_AXIS);

        statsPanel.setLayout(layoutBox);

        statsTitel = new JLabel("Statistics:");
        statsTitel.setFont(getFont().deriveFont(28.0f));
        statsTitel.setForeground(Color.BLACK);
        statsTitel.setBorder(new EmptyBorder(0,0,20,0));

        date = new JLabel("");
        date.setFont(getFont().deriveFont(18.0f));
        date.setForeground(Color.GRAY);

        week = new JLabel("");
        week.setFont(getFont().deriveFont(18.0f));
        week.setForeground(Color.GRAY);

        revenue = new JLabel("");
        revenue.setFont(getFont().deriveFont(18.0f));
        revenue.setForeground(Color.GRAY);

        parkedCars = new JLabel("");
        parkedCars.setFont(getFont().deriveFont(18.0f));
        parkedCars.setForeground(Color.GRAY);

        parkedSubCars = new JLabel("");
        parkedSubCars.setFont(getFont().deriveFont(18.0f));
        parkedSubCars.setForeground(Color.GRAY);

        averageParkingTime = new JLabel("");
        averageParkingTime.setFont(getFont().deriveFont(18.0f));
        averageParkingTime.setForeground(Color.GRAY);

        averageParkingPrice = new JLabel("");
        averageParkingPrice.setFont(getFont().deriveFont(18.0f));
        averageParkingPrice.setForeground(Color.GRAY);

        amountOfReservations = new JLabel("");
        amountOfReservations.setFont(getFont().deriveFont(18.0f));
        amountOfReservations.setForeground(Color.GRAY);

        amountAvailablePlaces = new JLabel("");
        amountAvailablePlaces.setFont(getFont().deriveFont(18.0f));
        amountAvailablePlaces.setForeground(Color.GRAY);

        amountOfCarsLeft = new JLabel("");
        amountOfCarsLeft.setFont(getFont().deriveFont(18.0f));
        amountOfCarsLeft.setForeground(Color.GRAY);

        weekRevenueLabel = new JLabel("");
        weekRevenueLabel.setFont(getFont().deriveFont(18.0f));
        weekRevenueLabel.setForeground(Color.GRAY);

        normalCarsInQueue = new JLabel("");
        returnStylesLabel(normalCarsInQueue);

        statsPanel.add(statsTitel);
        statsPanel.add(date);
        statsPanel.add(week);
        statsPanel.add(revenue);
        statsPanel.add(weekRevenueLabel);
        statsPanel.add(parkedCars);
        statsPanel.add(parkedSubCars);
        statsPanel.add(amountOfReservations);
        statsPanel.add(averageParkingTime);
        statsPanel.add(averageParkingPrice);
        statsPanel.add(amountAvailablePlaces);
        statsPanel.add(amountOfCarsLeft);
        statsPanel.add(normalCarsInQueue);


        add(statsPanel);

    }

    @Override
    public void tick() {

    }

    public JLabel returnStylesLabel(JLabel label) {
        label.setFont(getFont().deriveFont(18.0f));
        label.setForeground(Color.GRAY);

        return label;
    }

    @Override
    public void updateView() {
        this.date.setText("Date: " + this.controller.getDate());
        this.week.setText("Week: " + this.controller.getWeek());
        this.revenue.setText("Total revenue € " + this.controller.getRevenue());
        this.weekRevenueLabel.setText("Omzet per " + Time.getDayString() + ": €" + this.controller.getWeeklyRevenue()[Time.getDay()]);
        this.parkedCars.setText("Total parked cars: " + this.controller.getAmountOfParkedCars());
        this.amountOfCarsLeft.setText("Total cars left: " + this.controller.getTotalCarsLeft());
        this.amountAvailablePlaces.setText("Available places: " + this.controller.getNumberOfOpenSlots());
        this.parkedSubCars.setText("Total Parked subscribers: " + this.controller.getNumberOfParkedPassCars());
        this.averageParkingTime.setText("Average parking time: " + this.controller.getAverageTime() + "h");
        this.averageParkingPrice.setText("Average parking price: €" + this.controller.getAverageParkingPrice());
        this.amountOfReservations.setText("Total Current reservations: " + this.controller.getNumberOfReservedPlaces());
        this.normalCarsInQueue.setText("Normal cars in queue: " + this.controller.getNormalCarsInQueue());

//        this.setLegendaImage(this.createImage(150,250));
//        Graphics graphics = this.getLegendaImage().getGraphics();
//
//        this.drawPlace(graphics, 4, 4, Color.RED, "Reguliere auto");
//        this.drawPlace(graphics, 4, 5, Color.BLUE, "Abbonement houder");
//        this.drawPlace(graphics, 4, 6, Color.GREEN, "Gereserveerde plek");
//        this.drawPlace(graphics, 4 ,7, Color.WHITE, "Lege plek");
//        this.repaint();
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        if (legendaImage == null) {
//            return;
//        }
//
//        Dimension currentSize = getSize();
//        if (size.equals(currentSize)) {
//            g.drawImage(legendaImage, 0, 0, null);
//        }
//        else {
//            // Rescale the previous image.
//            g.drawImage(legendaImage, 0, 0, currentSize.width, currentSize.height, null);
//        }
//    }
//
//    public Image getLegendaImage() {
//        return this.legendaImage;
//    }
//
//    public void setLegendaImage(Image image) {
//        this.legendaImage = image;
//    }
//
//    public void drawPlace(Graphics graphics, int x, int y, Color color, String text) {
//        graphics.setColor(color);
//        graphics.fillRect(x + 5, 60 + (y * 22) + 10, 24, 10);
//        graphics.setColor(Color.black);
//        graphics.setFont(new Font("Arial", Font.PLAIN, 10));
//        graphics.drawString(text, x + 30 + 5, 55 + (y * 22) + 10 + 14);
//
//    }
}
