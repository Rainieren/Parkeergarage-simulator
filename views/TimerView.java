package views;

import controllers.TimerController;

import javax.swing.*;

public class TimerView extends AbstractView {

    private TimerController timerController;
    public JLabel timeLabel;

    public TimerView(TimerController timerController) {
        addView(this);
        this.timerController = timerController;
        this.timeLabel = new JLabel("");
        this.timeLabel.setFont(getFont().deriveFont(24.0f));
        add(this.timeLabel);
    }
    @Override
    public void tick() {

    }

    @Override
    public void updateView() {
        this.timeLabel.setText(this.timerController.getDay() + " " + this.timerController.getTime());
    }
}
