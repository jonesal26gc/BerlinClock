package BerlinClock;

import static java.lang.Thread.sleep;

/**
 * Created by xm39 on 15/12/2016.
 */
public class BerlinClockPerpetualApp {

    public static void main(String[] args) {
    /*********************************************************************************
     * Run the Berlin Clock application perpetually.
     *********************************************************************************/

        // The frequency of the update.
        int intervalDelayMilliseconds = 1000;
        final int intervalDelayMinutes=15;
        int maximumIterations = 40;
        int startHour = 9;
        int endHour = 17;

        BerlinClock b = new BerlinClock("");

        if ( intervalDelayMinutes != 0 ) {
            intervalDelayMilliseconds = intervalDelayMinutes * 60 * 1000;
        }

        // Loop around a number of times displaying the current (default option) time.
        int i = 0;
        while ( i < maximumIterations
                && b.getInHours() >= startHour
                && b.getInHours() <= endHour ){

            // Increment counter.
            i++;

            // Set the time and the indicators.
            b = new BerlinClock("");
            //b.setWindowDisplayInMilliSeconds(500);

            // Display the window.
            try { b.displayInWindow();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            // Wait a while before going around again.
            try { sleep(intervalDelayMilliseconds);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
