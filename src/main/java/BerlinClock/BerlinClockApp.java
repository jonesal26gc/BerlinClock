package BerlinClock;

@SuppressWarnings("deprecation")   // switches off the warning for the BerlinClock.BerlinClock.display() method.
public class BerlinClockApp {

    public static void main(String[] args) {
        /*********************************************************************************
         * Run the Berlin Clock application.
         *********************************************************************************/

        BerlinClock b = new BerlinClock();

        // If the parameters have not provided a time string, then use the current time.
        try {
            if (args.length == 0) {
                b.defaultTime();
            } else {
                b.setParameterTime(args[0]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Display the time that has been used.
        System.out.println(b.toString());

        // Format the indicators and display the clock for the specified time.
        b.calculateIndicators();
        b.display();
        try {
            b.setWindowDisplayInMilliSeconds(3000);
            b.displayInWindow();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
