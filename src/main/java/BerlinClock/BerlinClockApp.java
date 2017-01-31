package BerlinClock;

@SuppressWarnings("deprecation")   // switches off the warning for the BerlinClock.BerlinClock.displayInConsole() method.
public class BerlinClockApp {

    public static final int DISPLAY_IN_MILLI_SECONDS = 5000;

    public static void main(String[] args) {
        /*********************************************************************************
         * Run the Berlin Clock application.
         *********************************************************************************/

        // If the parameters have not provided a time string, then use the current time.
        BerlinClock berlinClock = createBerlinClock(args);

        // Display the time that has been used.
        System.out.println(berlinClock.toString());

        // Format the indicators and displayInConsole the clock for the specified time.
        System.out.println(berlinClock.displayInConsole());
        try {
            berlinClock.setWindowDisplayInMilliSeconds(DISPLAY_IN_MILLI_SECONDS);
            //berlinClock.displayInWindow();
            berlinClock.displayInWindowWithColouring();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static BerlinClock createBerlinClock(String[] args) {
        if (args.length == 0) {
            return new BerlinClock();
        }
        return new BerlinClock(args[0]);
    }
}
