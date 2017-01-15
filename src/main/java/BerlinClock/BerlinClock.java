package BerlinClock;

import Annotations.MethodInfo;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class BerlinClock {
    /*********************************************************************************
     * Display the time in the Berlin Clock format.
     *********************************************************************************/

    private String parameterTime;

    // Values derived from the input time string.
    private int inHours;
    private int inMinutes;
    private int inSeconds;

    // Boolean indicators for each of the light bulbs.
    private boolean indSecondInterval;
    private boolean[] ind5HrIntervals = new boolean[4];
    private boolean[] ind1HrIntervals = new boolean[4];
    private boolean[] ind5MinIntervals = new boolean[11];
    private boolean[] ind1MinIntervals = new boolean[4];

    // The output characters.
    private static final String RED_CHAR = "R";
    private static final String YELLOW_CHAR = "Y";
    private static final String OFF_CHAR = " ";
    private static final String NEW_LINE = "\n";

    //private static final String RED_CHAR = "\033[31;5;7mR\033[0m";
    //private static final String YELLOW_CHAR = "\033[33;5;7mY\033[0m";
    //private static final String OFF_CHAR = " ";

    // Font information.
    private static final String[] fontOptions = {"Serif", "Agency FB", "Arial", "Calibri", "Cambrian"
            , "Century Gothic", "Comic Sans MS", "Courier New"
            , "Forte", "Garamond", "Monospaced", "Segoe UI"
            , "Times New Roman", "Trebuchet MS", "Serif"};
    private static final int[] sizeOptions = {8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28};
    private int windowDisplayInMilliSeconds = 15000;

    private static final int REVISION = 1;

    public BerlinClock() {
    }

    public BerlinClock(String parameterTime) {
        /*********************************************************************************
         * This constructor sets the time and the clock.
         *********************************************************************************/

        try {
            if (parameterTime.equals("")) {
                defaultTime();
            } else {
                setParameterTime(parameterTime);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        calculateIndicators();
    }

    public String getParameterTime() {
        return parameterTime;
    }

    public int getInHours() {
        return inHours;
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

    public void setWindowDisplayInMilliSeconds(int windowDisplayInMilliSeconds) {
        if (windowDisplayInMilliSeconds <= 10000) {
            this.windowDisplayInMilliSeconds = windowDisplayInMilliSeconds;
        }
    }

    @MethodInfo(author = "TonyJ", comments = "setParameterTime", date = "2016-12-13", revision = 2)
    public void setParameterTime(String parameterTime) throws Exception {
        /*********************************************************************************
         * Set the current time, ensuring that it's valid.
         *********************************************************************************/
        Time24hFormatValidator time24hFormatValidator = new Time24hFormatValidator();
        if (time24hFormatValidator.validate(parameterTime)) {
            // set the current times.
            this.parameterTime = parameterTime;

            // Derive the other attributes from this time.
            this.inHours = Integer.parseInt(parameterTime.substring(0, 2));
            this.inMinutes = Integer.parseInt(parameterTime.substring(3, 5));
            this.inSeconds = Integer.parseInt(parameterTime.substring(6, 8));
        } else {
            throw new Exception("Invalid time provided an input parameter.");
        }

    }

    @MethodInfo(author = "TonyJ", comments = "defaultTime", date = "2016-12-13", revision = 2)
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
        }

    }

    @MethodInfo(author = "TonyJ", comments = "calculateIndicators", date = "2016-12-13", revision = 2)
    public void calculateIndicators() {
        /*********************************************************************************
         * Set indicators corresponding to all the bulbs that must be lit for the time.
         *********************************************************************************/

        // Set the odd/even second indicator.
        indSecondInterval = ! (((inSeconds / 2) * 2) == inSeconds);

        // Set 5hr interval indicators.
        int numberOf5HrIntervals = inHours / 5;
        for (int x = 0; x < ind5HrIntervals.length; x++) {
            if ((numberOf5HrIntervals) >= (x + 1)) {
                ind5HrIntervals[x] = true;
            } else {
                ind5HrIntervals[x] = false;
            }
        }

        // Set 1hr interval indicators.
        int numberOf1HrIntervals = inHours % 5;
        for (int x = 0; x < ind1HrIntervals.length; x++) {
            if ((numberOf1HrIntervals) >= (x + 1)) {
                ind1HrIntervals[x] = true;
            } else {
                ind1HrIntervals[x] = false;
            }
        }

        // Set 5min interval indicators.
        int numberOf5MinIntervals = inMinutes / 5;
        for (int x = 0; x < ind5MinIntervals.length; x++) {
            if ((numberOf5MinIntervals) >= (x + 1)) {
                ind5MinIntervals[x] = true;
            } else {
                ind5MinIntervals[x] = false;
            }
        }

        // Set 1min interval indicators.
        int numberOf1MinIntervals = inMinutes % 5;
        for (int x = 0; x < ind1MinIntervals.length; x++) {
            if ((numberOf1MinIntervals) >= (x + 1)) {
                ind1MinIntervals[x] = true;
            } else {
                ind1MinIntervals[x] = false;
            }
        }
    }

    @MethodInfo(author = "TonyJ", comments = "display", date = "2016-12-13", revision = 2)
    // @deprecated
    // The following method of display should no longer be used.
    // Rather, use the pop-up window instead.
    @Deprecated     // states that this method is "old hat" !
    public boolean display() {
        /*********************************************************************************
         * Display the Berlin Clock.
         *********************************************************************************/

        System.out.print(formatSecondBox(indSecondInterval));
        System.out.print(formatFourBoxes(Boolean.TRUE, ind5HrIntervals));
        System.out.print(formatFourBoxes(Boolean.TRUE, ind1HrIntervals));
        System.out.print(formatElevenBoxes(ind5MinIntervals));
        System.out.print(formatFourBoxes(Boolean.FALSE, ind1MinIntervals));

        return Boolean.TRUE;
    }

    @MethodInfo(author = "TonyJ", comments = "displayInWindow", date = "2016-12-13", revision = 2)
    public boolean displayInWindow() throws InterruptedException {
        /*********************************************************************************
         * Display the Berlin Clock in a new window.
         *********************************************************************************/

        // Declare a label field.
        JLabel labelField = new JLabel(getParameterTime());
        labelField.setHorizontalAlignment(SwingConstants.CENTER);

        // Declare a text area field.
        JTextArea textField = new JTextArea(17, 36);
        textField.append(formatSecondBox(indSecondInterval));
        textField.append(formatFourBoxes(Boolean.TRUE, ind5HrIntervals));
        textField.append(formatFourBoxes(Boolean.TRUE, ind1HrIntervals));
        textField.append(formatElevenBoxes(ind5MinIntervals));
        textField.append(formatFourBoxes(Boolean.FALSE, ind1MinIntervals));
        textField.setEditable(false);
        textField.setFont(new Font(fontOptions[7], Font.BOLD, sizeOptions[4]));

        // Declare and open the frame.
        JFrame frame = new JFrame("Berlin Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(labelField, BorderLayout.NORTH);
        frame.getContentPane().add(textField, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        // Sleep for a while with the display on the screen.
        Thread.sleep(windowDisplayInMilliSeconds);

        // close the screen.
        frame.dispose();

        return Boolean.TRUE;
    }

    public boolean displayInPane() throws InterruptedException {
        /*********************************************************************************
         * Display the Berlin Clock in a new window with colours.
         *********************************************************************************/

        // Declare a label field.
        JLabel labelField = new JLabel(getParameterTime());
        labelField.setHorizontalAlignment(SwingConstants.CENTER);

        // Declare a text area field.
        JTextPane textField = new JTextPane();

        // Concatenate the clock output.
        String printString = formatSecondBox(indSecondInterval) +
                formatFourBoxes(Boolean.TRUE, ind5HrIntervals) +
                formatFourBoxes(Boolean.TRUE, ind1HrIntervals) +
                formatElevenBoxes(ind5MinIntervals) +
                formatFourBoxes(Boolean.FALSE, ind1MinIntervals);

        // Loop through each characters and set the colour and revised shape to
        // provide a larger block.
        printString = printString.replace("  R  ", "RRRRR").replace("  Y  ", "YYYYY");
        if ( printString.contains("  YYYYY  ")) {
            printString = printString.replace("  YYYYY  "," YYYYYYY ");
            printString = printString.replace("*     *","* YYY *");
            System.out.println("adjusting the second display");
        }

        for (int i = 0; i < (printString.length()); i++) {
            char x = (printString.charAt(i));
            switch (x) {
                case 'R':
                    appendToPane(textField, String.valueOf((char) 9608), Color.RED);
                    break;
                case 'Y':
                    appendToPane(textField, String.valueOf((char) 9608), Color.yellow);
                    break;
                default:
                    appendToPane(textField, String.valueOf(x), Color.lightGray);
            }
        }

        textField.setEditable(false);
        //textField.setFont(new Font(fontOptions[7], Font.BOLD, sizeOptions[2]));

        // Declare and open the frame.
        JFrame frame = new JFrame("Berlin Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(labelField, BorderLayout.NORTH);
        frame.getContentPane().add(textField, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        // Sleep for a while with the display on the screen.
        Thread.sleep(windowDisplayInMilliSeconds);

        // close the screen.
        frame.dispose();

        return Boolean.TRUE;
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        /*****************************************************************
         * Change the colour of each character as necessary.
         */
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset;
        aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, fontOptions[7]);
        aset = sc.addAttribute(aset, StyleConstants.FontSize, sizeOptions[2]);
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    @MethodInfo(author = "TonyJ", comments = "formatSecondBox", date = "2016-12-13", revision = 2)
    public String formatSecondBox(boolean arg) {
        /*********************************************************************************
         * Format the image for the second.
         *********************************************************************************/

        // Set the output character.
        String displayChar;
        if (arg) {
            displayChar = YELLOW_CHAR;
        } else {
            displayChar = OFF_CHAR;
        }

        // Format the output to a string.
        String outputLines = NEW_LINE;
        outputLines = outputLines.concat("                 * *" + NEW_LINE);
        outputLines = outputLines.concat("               *     *" + NEW_LINE);
        outputLines = outputLines.concat("             *    " + displayChar + "    *" + NEW_LINE);
        outputLines = outputLines.concat("               *     *" + NEW_LINE);
        outputLines = outputLines.concat("                 * *");

        return outputLines;
    }

    @MethodInfo(author = "TonyJ", comments = "formatFourBoxes", date = "2016-12-13", revision = 2)
    public String formatFourBoxes(boolean hourIndicator, boolean[] args) {
        /*********************************************************************************
         * Format the image for either the Hour or Minutes where 4 boxes are required.
         * The purpose is provided in the parameters.
         *********************************************************************************/

        // Create an array of string that will be displayed, based upon the ON/OFF status
        // and positioning.
        String[] displayChar = new String[args.length];

        for (int x = 0; x < args.length; x++) {
            if (args[x]) {
                if (hourIndicator) {
                    displayChar[x] = RED_CHAR;
                } else {
                    displayChar[x] = YELLOW_CHAR;
                }
            } else {
                displayChar[x] = OFF_CHAR;
            }
        }

        // Format the output to a string.
        String outputLines = NEW_LINE;
        outputLines = outputLines.concat(
                "╔═══════╗╔═══════╗╔═══════╗╔═══════╗" +
                        NEW_LINE);
        for (String x : displayChar) {
            outputLines = outputLines.concat("║   " + x + "   ║");
        }
        outputLines = outputLines.concat(
                NEW_LINE +
                        "╚═══════╝╚═══════╝╚═══════╝╚═══════╝");

        return outputLines;
    }

    @MethodInfo(author = "TonyJ", comments = "formatElevenBoxes", date = "2016-12-13", revision = 2)
    public String formatElevenBoxes(boolean[] args) {
        /*********************************************************************************
         * Format the image for the 5 minute intervals where eleven boxes are required.
         *********************************************************************************/

        // Create an array of string that will be displayed, based upon the ON/OFF status
        // and positioning.
        String[] displayChar = new String[args.length];

        int y = 0;
        for (int x = 0; x < args.length; x++) {
            y++;
            if (args[x]) {
                if (y == 3 | y == 6 | y == 9) {
                    displayChar[x] = RED_CHAR;
                } else {
                    displayChar[x] = YELLOW_CHAR;
                }
            } else {
                displayChar[x] = OFF_CHAR;
            }
        }

        // Format the output to a string.
        String outputLines = NEW_LINE;
        outputLines = outputLines.concat(
                "╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗" +
                        NEW_LINE);

        y = 0;
        for (String x : displayChar) {
            y++;
            outputLines = outputLines.concat("║" + x + "║");
            if (y == 3 | y == 6 | y == 9) {
                outputLines = outputLines.concat(" ");
            }
        }

        outputLines = outputLines.concat(
                NEW_LINE +
                        "╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝");

        return outputLines;
    }

    @Override // States that this method overrides that of a super-class (i.e. Object.toString().
    @MethodInfo(author = "TonyJ", comments = "toString", date = "2016-12-13", revision = REVISION)
    public String toString() {
        /*********************************************************************************
         * Show all the values.
         *********************************************************************************/

        return "BerlinClock.BerlinClock{" +
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
