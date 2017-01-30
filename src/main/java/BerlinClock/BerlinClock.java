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

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

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
    private static final char RED_CHAR = 'R';
    private static final char YELLOW_CHAR = 'Y';
    private static final char OFF_CHAR = ' ';
    private static final String NEW_LINE = "\n";
    private static final int [] FIFTEEN_MINUTE_INTERVALS = new int [] {3,6,9};
    public static final boolean HOUR_INDICATOR = true;
    private static final boolean NON_HOUR_INDICATOR = false;

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
        setLampIndicators();
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

    @MethodInfo(author = "TonyJ", comments = "setLampIndicators", date = "2016-12-13", revision = 2)
    public void setLampIndicators() {

        // Set the odd/even second indicator.
        indSecondInterval = !(((inSeconds / 2) * 2) == inSeconds);

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
    public void display() {
        /*********************************************************************************
         * Display the Berlin Clock.
         *********************************************************************************/

        System.out.print(buildBoxForSecondDisplay(indSecondInterval));
        System.out.print(buildBoxForFourBoxDisplay(HOUR_INDICATOR, ind5HrIntervals));
        System.out.print(buildBoxForFourBoxDisplay(HOUR_INDICATOR, ind1HrIntervals));
        System.out.print(buildBoxForFifteenMinuteBoxDisplay(ind5MinIntervals));
        System.out.print(buildBoxForFourBoxDisplay(NON_HOUR_INDICATOR, ind1MinIntervals));
    }

    @MethodInfo(author = "TonyJ", comments = "displayInWindow", date = "2016-12-13", revision = 2)
    public void displayInWindow() throws InterruptedException {
        /*********************************************************************************
         * Display the Berlin Clock in a new window.
         *********************************************************************************/

        // Declare a label field.
        JLabel labelField = new JLabel(getParameterTime());
        labelField.setHorizontalAlignment(SwingConstants.CENTER);

        // Declare a text area field.
        JTextArea textField = new JTextArea(17, 36);
        textField.append(buildBoxForSecondDisplay(indSecondInterval).toString());
        textField.append(buildBoxForFourBoxDisplay(Boolean.TRUE, ind5HrIntervals).toString());
        textField.append(buildBoxForFourBoxDisplay(Boolean.TRUE, ind1HrIntervals).toString());
        textField.append(buildBoxForFifteenMinuteBoxDisplay(ind5MinIntervals).toString());
        textField.append(buildBoxForFourBoxDisplay(Boolean.FALSE, ind1MinIntervals).toString());
        textField.setEditable(false);
        textField.setFont(new Font(fontOptions[7], Font.BOLD, sizeOptions[4]));

        // Declare and open the frame.
        JFrame frame = new JFrame("Berlin Clock");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(labelField, BorderLayout.NORTH);
        frame.getContentPane().add(textField, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        // Sleep for a while with the display on the screen.
        Thread.sleep(windowDisplayInMilliSeconds);

        // close the screen.
        frame.dispose();
    }

    public void displayInPane() throws InterruptedException {
        /*********************************************************************************
         * Display the Berlin Clock in a new window with colours.
         *********************************************************************************/

        // Declare a label field.
        JLabel labelField = new JLabel(getParameterTime());
        labelField.setHorizontalAlignment(SwingConstants.CENTER);

        // Declare a text area field.
        JTextPane textField = new JTextPane();

        // Concatenate the clock output.
        String printString = buildBoxForSecondDisplay(indSecondInterval).toString() +
                buildBoxForFourBoxDisplay(Boolean.TRUE, ind5HrIntervals).toString() +
                buildBoxForFourBoxDisplay(Boolean.TRUE, ind1HrIntervals).toString() +
                buildBoxForFifteenMinuteBoxDisplay(ind5MinIntervals).toString() +
                buildBoxForFourBoxDisplay(Boolean.FALSE, ind1MinIntervals).toString();

        // Loop through each characters and set the colour and revised shape to
        // provide a larger block.
        printString = printString.replace("  R  ", "RRRRR").replace("  Y  ", "YYYYY");
        if (printString.contains("  YYYYY  ")) {
            printString = printString.replace("  YYYYY  ", " YYYYYYY ");
            printString = printString.replace("*     *", "* YYY *");
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
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(labelField, BorderLayout.NORTH);
        frame.getContentPane().add(textField, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        // Sleep for a while with the display on the screen.
        Thread.sleep(windowDisplayInMilliSeconds);

        // close the screen.
        frame.dispose();
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

    @MethodInfo(author = "TonyJ", comments = "buildBoxForSecondDisplay", date = "2016-12-13", revision = 2)
    public StringBuffer buildBoxForSecondDisplay(boolean isLightOnIndicator) {
        return new StringBuffer()
                .append(NEW_LINE)
                .append("                 * *")
                .append(NEW_LINE)
                .append("               *     *")
                .append(NEW_LINE)
                .append("             *    ")
                .append(getLampCharacterOfSecond(isLightOnIndicator))
                .append("    *")
                .append(NEW_LINE)
                .append("               *     *")
                .append(NEW_LINE)
                .append("                 * *");
    }

    private char getLampCharacterOfSecond(boolean isLightOnIndicator) {
        if (isLightOnIndicator) {
            return YELLOW_CHAR;
        }
        return OFF_CHAR;
    }

    @MethodInfo(author = "TonyJ", comments = "buildBoxForFourBoxDisplay", date = "2016-12-13", revision = 2)
    public StringBuffer buildBoxForFourBoxDisplay(boolean hourIndicator, boolean[] isLightOnIndicators) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer
                .append(NEW_LINE)
                .append("╔═══════╗╔═══════╗╔═══════╗╔═══════╗")
                .append(NEW_LINE);

        for (boolean isLightOnIndicator : isLightOnIndicators) {
            stringBuffer
                    .append("║   ")
                    .append(getLampCharacterOfFourBoxes(hourIndicator, isLightOnIndicator))
                    .append("   ║");
        }

        stringBuffer.append(NEW_LINE)
                .append("╚═══════╝╚═══════╝╚═══════╝╚═══════╝");

        return stringBuffer;
    }

    private char getLampCharacterOfFourBoxes(boolean hourIndicator, boolean isLightOnIndicator) {
        if (isLightOnIndicator) {
            if (hourIndicator) {
                return RED_CHAR;
            } else {
                return YELLOW_CHAR;
            }
        }
        return OFF_CHAR;
    }

    @MethodInfo(author = "TonyJ", comments = "buildBoxForFifteenMinuteBoxDisplay", date = "2016-12-13", revision = 2)
    public StringBuffer buildBoxForFifteenMinuteBoxDisplay(boolean[] isLightOnIndicators) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(NEW_LINE)
                .append("╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗")
                .append(NEW_LINE);

        int lampNumber = 0;
        for (boolean isLightOnIndicator : isLightOnIndicators) {
            lampNumber++;
            stringBuffer.append("║")
                    .append(getLampCharacterOfElevenBoxes(isLightOnIndicator, lampNumber))
                    .append("║");
            if (isFifteenMinuteInterval(lampNumber)) {
                stringBuffer.append(" ");
            }
        }

        stringBuffer.append(NEW_LINE)
                .append("╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝");

        return stringBuffer;
    }

    private char getLampCharacterOfElevenBoxes(boolean isLightOnIndicator, int lampNumber) {
        if (isLightOnIndicator) {
            if (isFifteenMinuteInterval(lampNumber)) {
                return RED_CHAR;
            } else {
                return YELLOW_CHAR;
            }
        }
        return OFF_CHAR;
    }

    private boolean isFifteenMinuteInterval(int lampNumber) {
        for (int fifteenMinuteInterval : FIFTEEN_MINUTE_INTERVALS) {
            if (lampNumber == fifteenMinuteInterval) {
                return true;
            }
        }
        return false;
    }

    @Override // States that this method overrides that of a super-class (i.e. Object.toString().
    @MethodInfo(author = "TonyJ", comments = "toString", date = "2016-12-13", revision = REVISION)
    public String toString() {
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
