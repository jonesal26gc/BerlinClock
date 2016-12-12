import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class BerlinClock {
    /*********************************************************************************
     * Display the time in the Berlin Clock format.
     *********************************************************************************/

    String parameterTime;

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
    private static final String NEW_LINE = "\n";

    //private static final String RED_CHAR = "\033[31;5;7mR\033[0m";
    //private static final String YELLOW_CHAR = "\033[33;5;7mY\033[0m";
    //private static final String OFF_CHAR = " ";


    public BerlinClock() {}

    public BerlinClock(String parameterTime) {
        /*********************************************************************************
         * This constructor sets the time and the clock.
         *********************************************************************************/

        try {
            if ( parameterTime.equals("") ) {
                defaultTime();
            } else {
                setParameterTime(parameterTime);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {}

        calculateIndicators();
    }

    public String getParameterTime() {
        return parameterTime;
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

    public void setParameterTime(String parameterTime) throws Exception {
        /*********************************************************************************
         * Set the current time, ensuring that it's valid.
         *********************************************************************************/
        Time24hFormatValidator time24hFormatValidator = new Time24hFormatValidator();
        if ( time24hFormatValidator.validate(parameterTime) ) {
            // set the current times.
            this.parameterTime = parameterTime;

            // Derive the other attributes from this time.
            this.inHours = Integer.parseInt(parameterTime.substring(0,2));
            this.inMinutes = Integer.parseInt(parameterTime.substring(3,5));
            this.inSeconds = Integer.parseInt(parameterTime.substring(6,8));
        } else {
            throw new Exception("Invalid time provided an input parameter.");
        }

    }

    public void defaultTime() {
        /*********************************************************************************
         * Set the current time, defaulting to the current time.
         *********************************************************************************/

        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Calendar cal1 = Calendar.getInstance();
            setParameterTime(sdf.format(cal1.getTime()));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {}

    }

    public void calculateIndicators() {
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

    public void display() {
        /*********************************************************************************
         * Display the Berlin Clock.
         *********************************************************************************/

        System.out.println(formatSecondBox());
        System.out.println(formatFourBoxes(Boolean.TRUE,ind5HrIntervals));
        System.out.println(formatFourBoxes(Boolean.TRUE,ind1HrIntervals));
        System.out.println(formatElevenBoxes(ind5MinIntervals));
        System.out.println(formatFourBoxes(Boolean.FALSE,ind1MinIntervals));
    }

    public String formatSecondBox() {
        /*********************************************************************************
         * Format the image for the second.
         *********************************************************************************/

        // Set the output character.
        String displayChar;
        if (indSecondInterval) {
            displayChar = YELLOW_CHAR;
        } else {
            displayChar = OFF_CHAR;
        }

        // Format the output to a string.
        String outputLines = "";
        outputLines = outputLines.concat("                 * *" + NEW_LINE);
        outputLines = outputLines.concat("               *     *" + NEW_LINE);
        outputLines = outputLines.concat("             *    "+displayChar+"    *" + NEW_LINE);
        outputLines = outputLines.concat("               *     *" + NEW_LINE);
        outputLines = outputLines.concat("                 * *");

        return outputLines;
    }

    public String formatFourBoxes(boolean hourIndicator, boolean [] args) {
        /*********************************************************************************
         * Format the image for either the Hour or Minutes where 4 boxes are required.
         * The purpose is provided in the parameters.
         *********************************************************************************/

        // Create an array of string that will be displayed, based upon the ON/OFF status
        // and positioning.
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

        // Format the output to a string.
        String outputLines = "";
        outputLines = outputLines.concat("╔═══════╗╔═══════╗╔═══════╗╔═══════╗" + NEW_LINE);
        for ( String x : displayChar ) {
            outputLines = outputLines.concat("║   "+x+"   ║");
        }
        outputLines = outputLines.concat(NEW_LINE + "╚═══════╝╚═══════╝╚═══════╝╚═══════╝");

        return outputLines;
    }

    public String formatElevenBoxes(boolean [] args) {
        /*********************************************************************************
         * Format the image for the 5 minute intervals where eleven boxes are required.
         *********************************************************************************/

        // Create an array of string that will be displayed, based upon the ON/OFF status
        // and positioning.
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

        // Format the output to a string.
        String outputLines = "";
        outputLines = outputLines.concat("╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗" + NEW_LINE);

        y = 0;
        for ( String x : displayChar ) {
            y++;
            outputLines = outputLines.concat("║"+x+"║");
            if (y==3 | y==6 | y==9) {
                outputLines = outputLines.concat(" ");
            }
        }

        outputLines = outputLines.concat(NEW_LINE + "╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝");

        return outputLines;
    }

    public String toString() {
        /*********************************************************************************
         * Show all the values.
         *********************************************************************************/

        return "BerlinClock{" +
                "parameterTime='" + parameterTime + '\'' +
                ", inHours=" + inHours +
                ", inMinutes=" + inMinutes +
                ", inSeconds=" + inSeconds +
                '}' + NEW_LINE +
                "indSecondInterval=" + indSecondInterval + NEW_LINE +
                "ind5HrIntervals=" + Arrays.toString(ind5HrIntervals) + NEW_LINE +
                "ind1HrIntervals=" + Arrays.toString(ind1HrIntervals) + NEW_LINE +
                "ind5MinIntervals=" + Arrays.toString(ind5MinIntervals) + NEW_LINE +
                "ind1MinIntervals=" + Arrays.toString(ind1MinIntervals);

    }

}
