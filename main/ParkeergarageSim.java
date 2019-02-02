package main;

import controllers.AbstractController;
import models.*;
import views.AbstractView;
import views.MainView;

public class ParkeergarageSim {

    private static int tickPause = 100;
    private static int runSpeed = 1;
    private static boolean running = false;

    public static void setRunning(boolean isRunning) {
        running = isRunning;
    }

    public static boolean isRunning() { return running;}

    public void run() {
        while (true) {

            if(running) {
                tick();
            }
            System.out.println();
        }
    }

    public static void setTickPause(int tick) { tickPause = tick;}


    private void tick() {
        Time.advanceTime();
        tickController();
//        handleExit();
        updateViews();
        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        handleEntrance();
        updateController();
    }

    private void updateViews() {
        for(AbstractView view: AbstractView.getViews()) {
            view.tick();
            view.updateView();
        }
    }

    private void tickController() {
        for(AbstractController controller: AbstractController.getControllers()) {
            controller.tick();
        }
    }

    public static void startSim() {
        running = true;
    }

    public static void stopSim() {
        running = false;
    }

    public static void setRunSpeed(int value) {
        runSpeed = value;
    }

    private void updateController() {
        for(AbstractController controller: AbstractController.getControllers()) {
            controller.updateController();
        }
    }


    public static void main(String args[]) {
        ParkeergarageSim parkeergarageSim = new ParkeergarageSim();
        MainView mainView = new MainView();
        parkeergarageSim.run();
    }
}
