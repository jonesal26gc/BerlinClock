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
        int startHour = 9;
        int endHour = 17;
        int intervalDelayMinutes=10;
        int maximumIterations = 100;

        int intervalDelayMilliseconds = 1000;

        // Use the parameters to adjust the run characteristics.
        if (args.length >= 4) {
            System.out.println("Parameter #1 (start hour) = '" + args[0] + "'");
            System.out.println("Parameter #2 (end hour)   = '" + args[1] + "'");
            System.out.println("Parameter #3 (interval)   = '" + args[2] + "'");
            System.out.println("Parameter #4 (iterations) = '" + args[3] + "'");

            int paramStartHour = Integer.parseInt(args[0]);
            int paramEndHour = Integer.parseInt(args[1]);
            int paramIntervalDelayMinutes = Integer.parseInt(args[2]);
            int paramMaximumIterations = Integer.parseInt(args[3]);
            if ( paramStartHour >= 8
               & paramEndHour <= 18
               & paramIntervalDelayMinutes >= 0
               & paramIntervalDelayMinutes <= 60
               & paramMaximumIterations    >= 0
               & paramMaximumIterations    <=1000 ) {
                startHour = paramStartHour;
                endHour = paramEndHour;
                intervalDelayMinutes = paramIntervalDelayMinutes;
                maximumIterations = paramMaximumIterations;
            } else {
                System.out.println("Error in input parameters=" +
                        paramStartHour + "/" + paramEndHour + "/" +
                        paramIntervalDelayMinutes + "/" + paramMaximumIterations);
                return;
            }
        }

        System.out.println("Starting");

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
            //b.setWindowDisplayInMilliSeconds(15000);

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

        System.out.println("Ending");
    }
}
