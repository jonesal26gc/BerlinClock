package BerlinClock;

@SuppressWarnings("deprecation")   // switches off the warning for the BerlinClock.BerlinClock.display() method.
public class BerlinClockApp {

    public static void main(String[] args) {
        /*********************************************************************************
         * Run the Berlin Clock application.
         *********************************************************************************/

        BerlinClock b = null;

        // If the parameters have not provided a time string, then use the current time.
        if (args.length == 0) {
            b = new BerlinClock("");
        } else {
            b = new BerlinClock(args[0]);
        }

        // Display the time that has been used.
        System.out.println(b.toString());

        // Format the indicators and display the clock for the specified time.
        b.display();
        try {
            b.setWindowDisplayInMilliSeconds(6000);
            //b.displayInWindow();
            b.displayInPane();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
