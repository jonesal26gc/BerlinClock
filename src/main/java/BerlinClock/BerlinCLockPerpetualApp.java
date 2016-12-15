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

        BerlinClock b;

        // Loop around a number of times displaying the current (default option) time.
        for (int i = 0; i < 2; i++) {

            // Set the time and the indicators.
            b = new BerlinClock("");
            //b.setWindowDisplayInMilliSeconds(500);

            // Display the window.
            try { b.displayInWindow();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            // Wait a while before going around again.
            try { sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
