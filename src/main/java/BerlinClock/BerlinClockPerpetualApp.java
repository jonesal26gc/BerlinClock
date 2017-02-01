package BerlinClock;

import static java.lang.Thread.sleep;

public class BerlinClockPerpetualApp {
    public static final int MILLISECONDS_PER_MINUTE = 60000;

    /*********************************************************************************
     * Run the Berlin Clock application perpetually.
     *********************************************************************************/

    public static void main(String[] args) {
        run(createBerlinClockPerpetualParameters(args));
    }

    private static BerlinClockPerpetualParameters createBerlinClockPerpetualParameters(String[] args) {
        if (args.length > 0) {
            return new BerlinClockPerpetualParameters(args);
        }
        return new BerlinClockPerpetualParameters();
    }

    private static void run(BerlinClockPerpetualParameters berlinClockPerpetualParameters) {
        int intervalBetweenDisplaysInMilliseconds = (berlinClockPerpetualParameters.getIntervalBetweenDisplaysInMinutes() * MILLISECONDS_PER_MINUTE);

        for (int iteration = 1; true; iteration++) {
            BerlinClock berlinClock = new BerlinClock();
            if (iteration > berlinClockPerpetualParameters.getMaximumIterationsForThisExecution()
                    | berlinClock.getHours() < berlinClockPerpetualParameters.getEarliestStartHour()
                    | berlinClock.getHours() > berlinClockPerpetualParameters.getLatestEndHour()) {
                break;
            }
            System.out.println("Display @ " + berlinClock.getParameterTime());
            StringBuffer console = new StringBuffer();
            berlinClock.display(console);
            display(intervalBetweenDisplaysInMilliseconds, berlinClock.getParameterTime(), console);
        }
    }

    private static void display(int intervalDelayMilliseconds, String parameterTime, StringBuffer console) {
        try {
            new BerlinClockWindowDisplay().displayWithColour(parameterTime, console);
            sleep(intervalDelayMilliseconds);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error - display failed.");
        }
    }
}

