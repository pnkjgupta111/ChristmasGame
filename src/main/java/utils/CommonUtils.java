package utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtils {
    public static int getRandom(final int max) {
        return (int) (Math.floor(max * Math.random()));
    }

    public static void sleepThreeSeconds() {
        try {
            Thread.sleep(3000);
        } catch (final InterruptedException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}
