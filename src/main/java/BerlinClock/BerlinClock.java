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
    private int hours;
    private int minutes;
    private int seconds;

    // Boolean indicators for each of the light bulbs.
    private boolean lampOnSecondIndicator;
    private boolean[] lampOn5HourIndicators = new boolean[4];
    private boolean[] lampOn1hourIndicators = new boolean[4];
    private boolean[] lampOn5MinuteIndicators = new boolean[11];
    private boolean[] lampOn1MinuteIndicators = new boolean[4];

    private static final char RED_CHAR = 'R';
    private static final char YELLOW_CHAR = 'Y';
    private static final char OFF_CHAR = ' ';
    private static final String NEW_LINE = "\n";
    private static final int[] FIVE_MINUTE_EXCEPTION_INTERVALS = new int[]{3, 6, 9};
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
        defaultTimeToNow();
        setLampOnIndicators();
    }

    public BerlinClock(String parameterTime) {
        try {
            setParameterTime(parameterTime);
            setLampOnIndicators();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getParameterTime() {
        return parameterTime;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public boolean getLampOnSecondIndicator() {
        return lampOnSecondIndicator;
    }

    public boolean[] getLampOn5HourIndicators() {
        return lampOn5HourIndicators;
    }

    public boolean[] getLampOn1hourIndicators() {
        return lampOn1hourIndicators;
    }

    public boolean[] getLampOn5MinuteIndicators() {
        return lampOn5MinuteIndicators;
    }

    public boolean[] getLampOn1MinuteIndicators() {
        return lampOn1MinuteIndicators;
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
            this.hours = Integer.parseInt(parameterTime.substring(0, 2));
            this.minutes = Integer.parseInt(parameterTime.substring(3, 5));
            this.seconds = Integer.parseInt(parameterTime.substring(6, 8));
        } else {
            throw new Exception("Invalid time provided an input parameter.");
        }
    }

    @MethodInfo(author = "TonyJ", comments = "defaultTimeToNow", date = "2016-12-13", revision = 2)
    public void defaultTimeToNow() {
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Calendar cal1 = Calendar.getInstance();
            setParameterTime(sdf.format(cal1.getTime()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @MethodInfo(author = "TonyJ", comments = "setLampOnIndicators", date = "2016-12-13", revision = 2)
    public void setLampOnIndicators() {

        // Set the odd/even second indicator.
        lampOnSecondIndicator = !(((seconds / 2) * 2) == seconds);

        // Set 5hr interval indicators.
        int numberOf5HrIntervals = hours / 5;
        for (int x = 0; x < lampOn5HourIndicators.length; x++) {
            if ((numberOf5HrIntervals) >= (x + 1)) {
                lampOn5HourIndicators[x] = true;
            } else {
                lampOn5HourIndicators[x] = false;
            }
        }

        // Set 1hr interval indicators.
        int numberOf1HrIntervals = hours % 5;
        for (int x = 0; x < lampOn1hourIndicators.length; x++) {
            if ((numberOf1HrIntervals) >= (x + 1)) {
                lampOn1hourIndicators[x] = true;
            } else {
                lampOn1hourIndicators[x] = false;
            }
        }

        // Set 5min interval indicators.
        int numberOf5MinIntervals = minutes / 5;
        for (int x = 0; x < lampOn5MinuteIndicators.length; x++) {
            if ((numberOf5MinIntervals) >= (x + 1)) {
                lampOn5MinuteIndicators[x] = true;
            } else {
                lampOn5MinuteIndicators[x] = false;
            }
        }

        // Set 1min interval indicators.
        int numberOf1MinIntervals = minutes % 5;
        for (int x = 0; x < lampOn1MinuteIndicators.length; x++) {
            if ((numberOf1MinIntervals) >= (x + 1)) {
                lampOn1MinuteIndicators[x] = true;
            } else {
                lampOn1MinuteIndicators[x] = false;
            }
        }
    }

    @MethodInfo(author = "TonyJ", comments = "displayInConsole", date = "2016-12-13", revision = 2)
    // @deprecated
    // The following method of displayInConsole should no longer be used.
    // Rather, use the pop-up window instead.
    @Deprecated     // states that this method is "old hat" !
    public void displayInConsole() {
        /*********************************************************************************
         * Display the Berlin Clock.
         *********************************************************************************/

        System.out.print(buildBoxForSecondDisplay(lampOnSecondIndicator));
        System.out.print(buildBoxesForFourBoxDisplay(HOUR_INDICATOR, lampOn5HourIndicators));
        System.out.print(buildBoxesForFourBoxDisplay(HOUR_INDICATOR, lampOn1hourIndicators));
        System.out.print(buildBoxesForFiveMinuteDisplay(lampOn5MinuteIndicators));
        System.out.print(buildBoxesForFourBoxDisplay(NON_HOUR_INDICATOR, lampOn1MinuteIndicators));
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
        textField.append(buildBoxForSecondDisplay(lampOnSecondIndicator).toString());
        textField.append(buildBoxesForFourBoxDisplay(HOUR_INDICATOR, lampOn5HourIndicators).toString());
        textField.append(buildBoxesForFourBoxDisplay(HOUR_INDICATOR, lampOn1hourIndicators).toString());
        textField.append(buildBoxesForFiveMinuteDisplay(lampOn5MinuteIndicators).toString());
        textField.append(buildBoxesForFourBoxDisplay(NON_HOUR_INDICATOR, lampOn1MinuteIndicators).toString());
        textField.setEditable(false);
        textField.setFont(new Font(fontOptions[7], Font.BOLD, sizeOptions[4]));

        // Declare and open the frame.
        JFrame frame = new JFrame("Berlin Clock");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.getContentPane().add(labelField, BorderLayout.NORTH);
        frame.getContentPane().add(textField, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        // Sleep for a while with the displayInConsole on the screen.
        Thread.sleep(windowDisplayInMilliSeconds);

        // close the screen.
        frame.dispose();
    }

    public void displayInWindowWithColouring() throws InterruptedException {
        /*********************************************************************************
         * Display the Berlin Clock in a new window with colours.
         *********************************************************************************/

        // Declare a label field.
        JLabel labelField = new JLabel(getParameterTime());
        labelField.setHorizontalAlignment(SwingConstants.CENTER);

        // Declare a text area field.
        JTextPane textField = new JTextPane();

        // Concatenate the clock output.
        String printString = buildBoxForSecondDisplay(lampOnSecondIndicator).toString() +
                buildBoxesForFourBoxDisplay(HOUR_INDICATOR, lampOn5HourIndicators).toString() +
                buildBoxesForFourBoxDisplay(HOUR_INDICATOR, lampOn1hourIndicators).toString() +
                buildBoxesForFiveMinuteDisplay(lampOn5MinuteIndicators).toString() +
                buildBoxesForFourBoxDisplay(NON_HOUR_INDICATOR, lampOn1MinuteIndicators).toString();

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

        // Sleep for a while with the displayInConsole on the screen.
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
    public StringBuffer buildBoxForSecondDisplay(boolean lampOnIndicator) {
        return new StringBuffer()
                .append(NEW_LINE)
                .append("                 * *")
                .append(NEW_LINE)
                .append("               *     *")
                .append(NEW_LINE)
                .append("             *    ")
                .append(getLampCharacterOfSecondDisplay(lampOnIndicator))
                .append("    *")
                .append(NEW_LINE)
                .append("               *     *")
                .append(NEW_LINE)
                .append("                 * *");
    }

    private char getLampCharacterOfSecondDisplay(boolean lampOnIndicator) {
        if (lampOnIndicator) {
            return YELLOW_CHAR;
        }
        return OFF_CHAR;
    }

    @MethodInfo(author = "TonyJ", comments = "buildBoxesForFourBoxDisplay", date = "2016-12-13", revision = 2)
    public StringBuffer buildBoxesForFourBoxDisplay(boolean hourIndicator, boolean[] lampOnIndicators) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer
                .append(NEW_LINE)
                .append("╔═══════╗╔═══════╗╔═══════╗╔═══════╗")
                .append(NEW_LINE);

        for (boolean lampOnIndicator : lampOnIndicators) {
            stringBuffer
                    .append("║   ")
                    .append(getLampCharacterForFourBoxDisplay(hourIndicator, lampOnIndicator))
                    .append("   ║");
        }

        stringBuffer.append(NEW_LINE)
                .append("╚═══════╝╚═══════╝╚═══════╝╚═══════╝");

        return stringBuffer;
    }

    private char getLampCharacterForFourBoxDisplay(boolean hourIndicator, boolean lampOnIndicator) {
        if (lampOnIndicator) {
            if (hourIndicator) {
                return RED_CHAR;
            } else {
                return YELLOW_CHAR;
            }
        }
        return OFF_CHAR;
    }

    @MethodInfo(author = "TonyJ", comments = "buildBoxesForFiveMinuteDisplay", date = "2016-12-13", revision = 2)
    public StringBuffer buildBoxesForFiveMinuteDisplay(boolean[] lampOnIndicators) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(NEW_LINE)
                .append("╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗")
                .append(NEW_LINE);

        int lampNumber = 0;
        for (boolean lampOnIndicator : lampOnIndicators) {
            lampNumber++;
            stringBuffer.append("║")
                    .append(getLampCharacterForFiveMinuteDisplay(lampOnIndicator, lampNumber))
                    .append("║");
            if (isFiveMinuteExceptionInterval(lampNumber)) {
                stringBuffer.append(" ");
            }
        }

        stringBuffer.append(NEW_LINE)
                .append("╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝");

        return stringBuffer;
    }

    private char getLampCharacterForFiveMinuteDisplay(boolean lampOnIndicator, int lampNumber) {
        if (lampOnIndicator) {
            if (isFiveMinuteExceptionInterval(lampNumber)) {
                return RED_CHAR;
            } else {
                return YELLOW_CHAR;
            }
        }
        return OFF_CHAR;
    }

    private boolean isFiveMinuteExceptionInterval(int lampNumber) {
        for (int fiveMinuteExceptionInterval : FIVE_MINUTE_EXCEPTION_INTERVALS) {
            if (lampNumber == fiveMinuteExceptionInterval) {
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
                ", hours=" + hours +
                ", minutes=" + minutes +
                ", seconds=" + seconds +
                '}' + NEW_LINE +
                "lampOnSecondIndicator=" + lampOnSecondIndicator + NEW_LINE +
                "lampOn5HourIndicators=" + Arrays.toString(lampOn5HourIndicators) + NEW_LINE +
                "lampOn1hourIndicators=" + Arrays.toString(lampOn1hourIndicators) + NEW_LINE +
                "lampOn5MinuteIndicators=" + Arrays.toString(lampOn5MinuteIndicators) + NEW_LINE +
                "lampOn1MinuteIndicators=" + Arrays.toString(lampOn1MinuteIndicators);
    }
}
