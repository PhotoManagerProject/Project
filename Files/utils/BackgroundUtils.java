package Files.utils;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BackgroundUtils {

    public static void initBackground(JPanel panel) {
        panel.setBackground(new Color(204, 255, 255));
    }

    // εμγανίζει τυχαίο χρώμα φόντου με τιμές RGB
    public static void applyRandomBackground(JPanel panel) {
        Random random = new Random();
        panel.setBackground(new Color(random.nextInt(256), random.nextInt(256),
                random.nextInt(256)));
    }
}
