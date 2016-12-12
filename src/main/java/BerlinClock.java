import java.util.Arrays;

public class BerlinClock {
    /*********************************************************************************
     * Display the time in the Berlin Clock format.
     *********************************************************************************/

    String currentTime;

    // Values derived from the input time string.
    private int inHours;
    private int inMinutes;
    private int inSeconds;

    // Boolean indicators for each of the light bulbs.
    private boolean indSecondInterval;
    private boolean [] ind5HrIntervals = new boolean [4];
    private boolean [] ind1HrIntervals = new boolean [4];
    private boolean [] ind5MinIntervals = new boolean [11];
    private boolean [] ind1MinIntervals = new boolean [4];

    // The output characters.
    private static final String RED_CHAR = "R";
    private static final String YELLOW_CHAR = "Y";
    private static final String OFF_CHAR = "O";

    //private static final String RED_CHAR = "\033[31;5;7mR\033[0m";
    //private static final String YELLOW_CHAR = "\033[33;5;7mY\033[0m";
    //private static final String OFF_CHAR = " ";


    public BerlinClock() {
    }

    public BerlinClock(String currentTime) {
        /*********************************************************************************
         * This constructor sets the time and the clock.
         *********************************************************************************/

        try {
            setCurrentTime(currentTime);
            setBerlinClock();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {}
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public boolean getIndSecondInterval() {
        return indSecondInterval;
    }

    public boolean[] getInd5HrIntervals() {
        return ind5HrIntervals;
    }

    public boolean[] getInd1HrIntervals() {
        return ind1HrIntervals;
    }

    public boolean[] getInd5MinIntervals() {
        return ind5MinIntervals;
    }

    public boolean[] getInd1MinIntervals() {
        return ind1MinIntervals;
    }

    public void setCurrentTime(String currentTime) throws Exception {
        /*********************************************************************************
         * Set the current time, ensuring that it's valid.
         *********************************************************************************/
        Time24hFormatValidator time24hFormatValidator = new Time24hFormatValidator();
        if ( time24hFormatValidator.validate(currentTime) ) {
            // set the current times.
            this.currentTime = currentTime;

            // Derive the other attributes from this time.
            this.inHours = Integer.parseInt(currentTime.substring(0,2));
            this.inMinutes = Integer.parseInt(currentTime.substring(3,5));
            this.inSeconds = Integer.parseInt(currentTime.substring(6,8));
        } else {
            throw new Exception("Invalid time provided an input parameter.");
        }

    }

    public void setBerlinClock() {
        /*********************************************************************************
         * Set indicators corresponding to all the bulbs that must be lit for the time.
         *********************************************************************************/

        // Set the odd/even second indicator.
        indSecondInterval = ( ( ( inSeconds / 2) * 2 ) == inSeconds );

        // Set 5hr interval indicators.
        int accumulatedHours = 0;
        for ( int x = 0 ; x < ind5HrIntervals.length ; x++) {
            if  (((x + 1) * 5) <= inHours) {
                ind5HrIntervals[x] = true;
                accumulatedHours=accumulatedHours+5;
            } else {
                ind5HrIntervals[x] = false;
            }
        }

        // Set 1hr interval indicators.
        for ( int x = 0 ; x < ind1HrIntervals.length ; x++) {
            ind1HrIntervals[x] = (x + 1) <= ( inHours - accumulatedHours );
        }

        // Set 5min interval indicators.
        int accumulatedMinutes = 0;
        for ( int x = 0 ; x < ind5MinIntervals.length ; x++) {
            if (((x + 1) * 5) <= inMinutes) {
                ind5MinIntervals[x] = true ;
                accumulatedMinutes=accumulatedMinutes+5;
            } else {
                ind5MinIntervals[x] = false ;
            }
        }

        // Set 1min interval indicators.
        for ( int x = 0 ; x < ind1MinIntervals.length ; x++) {
            ind1MinIntervals[x] = (x + 1) <= ( inMinutes - accumulatedMinutes );
        }

    }

    public void displayBerlinClock() {
        /*********************************************************************************
         * Display the Berlin Clock.
         *********************************************************************************/

        displaySecond();
        displayFourBoxes(Boolean.TRUE,ind5HrIntervals);
        displayFourBoxes(Boolean.TRUE,ind1HrIntervals);
        displayElevenBoxes(ind5MinIntervals);
        displayFourBoxes(Boolean.FALSE,ind1MinIntervals);
    }

    public void displaySecond() {
        /*********************************************************************************
         * Display the image for the second.
         *********************************************************************************/

        String displayChar;
        if (indSecondInterval) {
            displayChar = YELLOW_CHAR;
        } else {
            displayChar = OFF_CHAR;
        }

        System.out.println("                 * *");
        System.out.println("               *     *");
        System.out.println("             *    "+displayChar+"    *");
        System.out.println("               *     *");
        System.out.println("                 * *");
    }

    public void displayFourBoxes(boolean hourIndicator, boolean [] args) {
        /*********************************************************************************
         * Display the image for either the Hour or Minutes where 4 boxes are required.
         * The purpose is provided in the parameters.
         *********************************************************************************/

        String [] displayChar = new String[args.length];

        for (int x = 0; x < args.length ; x++) {
            if (args[x]) {
                if ( hourIndicator ) {
                    displayChar[x] = RED_CHAR;
                } else {
                    displayChar[x] = YELLOW_CHAR;
                }
            } else {
                displayChar[x] = OFF_CHAR;
            }
        }

        System.out.println("╔═══════╗╔═══════╗╔═══════╗╔═══════╗");
        for ( String x : displayChar ) { System.out.print("║   "+x+"   ║"); }
        System.out.println("\n╚═══════╝╚═══════╝╚═══════╝╚═══════╝");

    }

    public void displayElevenBoxes(boolean [] args) {
        /*********************************************************************************
         * Display the image for the 5 minute intervals where eleven boxes are required.
         *********************************************************************************/

        String [] displayChar = new String [args.length];

        int y = 0;
        for (int x = 0; x < args.length ; x++) {
            y++;
            if (args[x]) {
                if (y==3 | y==6 | y==9) {
                    displayChar[x] = RED_CHAR;
                } else {
                    displayChar[x] = YELLOW_CHAR;
                }
            } else {
                displayChar[x] = OFF_CHAR;
            }
        }

        System.out.println("╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗");

        y = 0;
        for ( String x : displayChar ) {
            y++;
            System.out.print("║"+x+"║");
            if (y==3 | y==6 | y==9) { System.out.print(" ");}
        }

        System.out.println("\n╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝");

    }

    @Override
    public String toString() {
        /*********************************************************************************
         * Show all the values.
         *********************************************************************************/

        return "BerlinClock{" +
                "currentTime='" + currentTime + '\'' +
                ", inHours=" + inHours +
                ", inMinutes=" + inMinutes +
                ", inSeconds=" + inSeconds +
                "\n, BulbSecondInterval=" + indSecondInterval +
                "\n, ind5HrIntervals=" + Arrays.toString(ind5HrIntervals) +
                "\n, ind1HrIntervals=" + Arrays.toString(ind1HrIntervals) +
                "\n, ind5MinIntervals=" + Arrays.toString(ind5MinIntervals) +
                "\n, ind1MinIntervals=" + Arrays.toString(ind1MinIntervals) +
                '}';
    }
}
