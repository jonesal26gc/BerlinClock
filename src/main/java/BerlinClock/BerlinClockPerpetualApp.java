package BerlinClock;

import static java.lang.Thread.sleep;

public class BerlinClockPerpetualApp {
    private static final int MILLISECONDS_PER_MINUTE = 60000;

    /*********************************************************************************
     * Run the Berlin Clock application perpetually.
     *********************************************************************************/

    public static void main(String[] args) throws Exception {
        run(new BerlinClockPerpetualParameters(args));
    }

    private static void run(BerlinClockPerpetualParameters berlinClockPerpetualParameters) throws Exception {
        int intervalBetweenDisplaysInMilliseconds =
                (berlinClockPerpetualParameters.getIntervalBetweenDisplaysInMinutes()
                        * MILLISECONDS_PER_MINUTE);

        for (int iteration = 1; iteration < berlinClockPerpetualParameters.getMaximumIterationsForThisExecution(); iteration++) {
            BerlinClock berlinClock = new BerlinClock();
            if (berlinClock.getHours() < berlinClockPerpetualParameters.getEarliestHour()
                    | berlinClock.getHours() > berlinClockPerpetualParameters.getLatestHour()) {
                break;
            }
            display(intervalBetweenDisplaysInMilliseconds, berlinClock);
        }
    }

    private static void display(int intervalBetweenDisplaysInMilliseconds, BerlinClock berlinClock) throws InterruptedException {
        System.out.println("Display @ " + berlinClock.getParameterTime());
        StringBuffer console = new StringBuffer();
        berlinClock.display(console);
        BerlinClockWindowDisplay.displayWithColour(berlinClock.getParameterTime(), console);
        sleep(intervalBetweenDisplaysInMilliseconds);
    }
}
