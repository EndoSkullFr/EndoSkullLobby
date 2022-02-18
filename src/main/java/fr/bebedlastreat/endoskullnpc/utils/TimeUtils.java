package fr.bebedlastreat.endoskullnpc.utils;

public class TimeUtils {

    public static String getTime(long time) {
        int minutes = 0;
        long seconds = 0;
        while (time >= 1000) {
            seconds++;
            time -= 1000;
        }
        while (seconds >= 60) {
            minutes++;
            seconds -= 60;
        }
        if (minutes > 0) {
            return minutes + "m " + seconds + "s " + addZero(time) + "ms";
        } else if (seconds > 0) {
            return seconds + "s " + addZero(time) + "ms";
        }
        return addZero(time) + "ms";
    }

    private static String addZero(long number) {
        if (number < 10) {
            return "00" + number;
        }
        if (number < 100) {
            return "0" + number;
        }
        return String.valueOf(number);
    }
}
