package Files.utils;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoDateTimeUtils {
    private final static String TIME_FORMAT = "HH:mm:ss";
    private final static String DATE_FORMAT = "yyyy-MM-dd";

    public static void updateClock(JLabel timeLabel) {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
        String showTime = formatter.format(new Date());
        timeLabel.setText(showTime);
    }

    public static void displayDate(JLabel dateLabel) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String showTime = formatter.format(new Date());
        dateLabel.setText(showTime);
    }
}