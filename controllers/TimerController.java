package controllers;

import models.Time;
import views.TimerView;

import javax.swing.border.EmptyBorder;

public class TimerController extends AbstractController {

    private TimerView timerView;

    public TimerController() {
        addController(this);
        timerView = new TimerView(this);
        timerView.setBorder(new EmptyBorder(10,10,10,10));
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateController() {

    }

    public String getTime() {
        return Time.getTime();
    }

    public String getDay() {
        return Time.getDayString();
    }

    public TimerView getTimerView() {
        return timerView;
    }
}
