package models;

import java.util.ArrayList;

public class Event {

    public static ArrayList<Event> events = new ArrayList<>();
    private int plannedDate;
    private int endDate;
    private boolean event;

    public Event(int day, int period){
        this.plannedDate = day;
        this.endDate = plannedDate + period;

        events.add(new Event(15, 5));
    }

    public static boolean eventCheck(){
        for(Event event: Event.events) {
            if(event.plannedDate == Time.getDayRun()) {
                return true;
            }
        }
        return false;
    }
    public int getPlannedDate() {
        return plannedDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }
}