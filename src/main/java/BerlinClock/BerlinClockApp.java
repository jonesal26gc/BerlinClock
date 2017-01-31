package BerlinClock;

@SuppressWarnings("deprecation")   // switches off the warning for the BerlinClock.BerlinClock.display() method.
public class BerlinClockApp {

    public static void main(String[] args) {
        /*********************************************************************************
         * Run the Berlin Clock application.
         *********************************************************************************/

        // If the parameters have not provided a time string, then use the current time.
        BerlinClock berlinClock = createBerlinClock(args);

        // Display the time that has been used.
        System.out.println(berlinClock.toString());

        // Format the indicators and display the clock for the specified time.
        StringBuffer console = new StringBuffer();
        berlinClock.display(console);
        System.out.println(console.toString());

        try {
            new BerlinClockSpecialDisplay().displayInWindow(berlinClock.getParameterTime(),console);
            new BerlinClockSpecialDisplay().displayInWindowWithColouring(berlinClock.getParameterTime(),console);
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
