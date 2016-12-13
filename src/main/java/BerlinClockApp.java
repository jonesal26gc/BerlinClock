
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

        // Format the indicators and display the clock.
        b.calculateIndicators();
        b.display();

    }
}
