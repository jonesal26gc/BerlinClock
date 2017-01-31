package BerlinClock;

import static java.lang.Thread.sleep;

public class BerlinClockPerpetualApp {

    public static void main(String[] args) {
        int earliestStartHour = 9;
        int latestEndHour = 17;
        int intervalBetweenDisplaysInMinutes = 10;
        int maximumIterationsForThisExecution = 100;
        int intervalDelayMilliseconds = 60000;
        /*********************************************************************************
         * Run the Berlin Clock application perpetually.
         *********************************************************************************/

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
            if (paramStartHour >= 0
                    & paramEndHour <= 23
                    & paramIntervalDelayMinutes >= 0
                    & paramIntervalDelayMinutes <= 60
                    & paramMaximumIterations >= 0
                    & paramMaximumIterations <= 1000) {
                earliestStartHour = paramStartHour;
                latestEndHour = paramEndHour;
                intervalBetweenDisplaysInMinutes = paramIntervalDelayMinutes;
                maximumIterationsForThisExecution = paramMaximumIterations;
            } else {
                throw new RuntimeException("Error in input parameters=" +
                        paramStartHour + "/" + paramEndHour + "/" +
                        paramIntervalDelayMinutes + "/" + paramMaximumIterations);
            }
        }

        if (intervalBetweenDisplaysInMinutes != 0) {
            intervalDelayMilliseconds = intervalBetweenDisplaysInMinutes * 60 * 1000;
        }

        for (int i = 1; true; i++) {
            BerlinClock berlinClock = new BerlinClock();
            if (i > maximumIterationsForThisExecution
                    | berlinClock.getHours() < earliestStartHour
                    | berlinClock.getHours() > latestEndHour) {
                break;
            }
            display(intervalDelayMilliseconds, berlinClock);
        }
    }

    private static void display(int intervalDelayMilliseconds, BerlinClock berlinClock) {
        System.out.println("Display @ " + berlinClock.getParameterTime());
        StringBuffer console = new StringBuffer();
        berlinClock.display(console);

        try {
            new BerlinClockWindowDisplay().displayWithColour(berlinClock.getParameterTime(), console);
            sleep(intervalDelayMilliseconds);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error - display failed.");
        }
    }
}

