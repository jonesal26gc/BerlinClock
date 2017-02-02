package BerlinClock;

public class BerlinClockApp {

    public static void main(String[] args) {

        BerlinClock berlinClock = createBerlinClock(args);
        StringBuffer console = new StringBuffer();
        berlinClock.display(console);
        System.out.println(console.toString());

        try {
            BerlinClockWindowDisplay.displayWithLetters(berlinClock.getParameterTime(),console);
            BerlinClockWindowDisplay.displayWithColour(berlinClock.getParameterTime(),console);
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
