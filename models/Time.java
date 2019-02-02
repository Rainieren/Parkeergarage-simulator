package models;

public class Time {

    private static int day = 0;
    private static int dayRun = 0;
    private static int hour = 0;
    private static int minute = 0;
    private static int week = 1;
    private static int month = 1;
    private static int year = 2019;

    private static String dayString;

    public static int getDay() {
        return day;
    }

    public static String getDayString() {
        switch(day) {
            case 0:
                return dayString = "Maandag";
            case 1:
                return dayString = "Dinsdag";
            case 2:
                return dayString = "Woensdag";
            case 3:
                return dayString = "Donderdag";
            case 4:
                return dayString = "Vrijdag";
            case 5:
                return dayString = "Zaterdag";
            case 6:
                return dayString = "Zondag";
            default:
                return dayString = "Dag niet gevonden";
        }
    }

    public static int getHour() {
        return hour;
    }

    public static int getMinute() {
        return minute;
    }

    public static int getWeek() { return week;}

    public static int getMonth() { return month; }

    public static int getYear() { return year; }

    public static int getDayRun() { return dayRun; }

    public static String getTime() { return String.format("%02d:%02d", getHour(), getMinute()); }

    public static String getDate() { return String.format("%02d/%04d", getMonth(), getYear()); }

    public static void advanceTime() {
        // Advance the time by one minute.
        minute++;

        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
            dayRun++;
            SimulatorModel.arrivalVariation();
        }
        while (day > 6) {
            day -= 7;
            week++;
            SimulatorModel.eventMultiplier();
        } while (week > 4) {
            week -= 4;
            month++;
        } while (month > 12) {
            month -= 13;
            year++;
        }
    }

}
