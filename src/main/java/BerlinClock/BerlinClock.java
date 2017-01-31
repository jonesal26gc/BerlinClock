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
    private static final int REVISION = 1;
    public static final boolean HOUR_INDICATOR = true;
    private static final char RED_CHAR = 'R';
    private static final char YELLOW_CHAR = 'Y';
    private static final char OFF_CHAR = ' ';
    private static final String NEW_LINE = "\n";
    private static final int[] FIVE_MINUTE_EXCEPTION_INTERVALS = new int[]{3, 6, 9};
    private static final boolean NON_HOUR_INDICATOR = false;
    private static final String[] fontOptions = {"Serif", "Agency FB", "Arial", "Calibri", "Cambrian"
            , "Century Gothic", "Comic Sans MS", "Courier New"
            , "Forte", "Garamond", "Monospaced", "Segoe UI"
            , "Times New Roman", "Trebuchet MS", "Serif"};
    private static final int[] sizeOptions = {8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28};
    private String parameterTime;
    private int hours;
    private int minutes;
    private int seconds;
    private boolean lampOnSecondIndicator;
    private boolean[] lampOn5HourIndicators = new boolean[4];
    private boolean[] lampOn1hourIndicators = new boolean[4];
    private boolean[] lampOn5MinuteIndicators = new boolean[11];
    private boolean[] lampOn1MinuteIndicators = new boolean[4];
    private int windowDisplayInMilliSeconds = 15000;

    public BerlinClock() {
        defaultTimeToNow();
        setLampOnIndicators();
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
        setLampOnSecondIndicator();
        setLampOn5HourIndicators();
        setLampOn1HourIndicators();
        setLampOn5MinuteIndicators();
        setLampOn1MinuteIndicators();
    }

    private void setLampOnSecondIndicator() {
        lampOnSecondIndicator = !(((seconds / 2) * 2) == seconds);
    }

    private void setLampOn5HourIndicators() {
        int numberOf5HrIntervals = hours / 5;
        for (int indicator = 0; indicator < lampOn5HourIndicators.length; indicator++) {
            lampOn5HourIndicators[indicator] = (indicator < numberOf5HrIntervals);
        }
    }

    private void setLampOn1HourIndicators() {
        int numberOf1HrIntervals = hours % 5;
        for (int indicator = 0; indicator < lampOn1hourIndicators.length; indicator++) {
            lampOn1hourIndicators[indicator] = (indicator < numberOf1HrIntervals);
        }
    }

    private void setLampOn5MinuteIndicators() {
        int numberOf5MinIntervals = minutes / 5;
        for (int indicator = 0; indicator < lampOn5MinuteIndicators.length; indicator++) {
            lampOn5MinuteIndicators[indicator] = (indicator < numberOf5MinIntervals);
        }
    }

    private void setLampOn1MinuteIndicators() {
        int numberOf1MinIntervals = minutes % 5;
        for (int indicator = 0; indicator < lampOn1MinuteIndicators.length; indicator++) {
            lampOn1MinuteIndicators[indicator] = (indicator < numberOf1MinIntervals);
        }
    }

    public BerlinClock(String parameterTime) {
        try {
            setParameterTime(parameterTime);
            setLampOnIndicators();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getHours() {
        return hours;
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

    @MethodInfo(author = "TonyJ", comments = "displayInConsole", date = "2016-12-13", revision = 2)
    public StringBuffer displayInConsole() {
        return new StringBuffer()
                .append(buildBoxForSecondDisplay(lampOnSecondIndicator))
                .append(buildBoxesForFourBoxDisplay(HOUR_INDICATOR, lampOn5HourIndicators))
                .append(buildBoxesForFourBoxDisplay(HOUR_INDICATOR, lampOn1hourIndicators))
                .append(buildBoxesForFiveMinuteDisplay(lampOn5MinuteIndicators))
                .append(buildBoxesForFourBoxDisplay(NON_HOUR_INDICATOR, lampOn1MinuteIndicators));
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

    private char getLampCharacterOfSecondDisplay(boolean lampOnIndicator) {
        if (lampOnIndicator) {
            return YELLOW_CHAR;
        }
        return OFF_CHAR;
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

    @MethodInfo(author = "TonyJ", comments = "displayInWindow", date = "2016-12-13", revision = 2)
    public void displayInWindow() throws InterruptedException {
        // Declare a label field.
        JLabel labelField = new JLabel(getParameterTime());
        labelField.setHorizontalAlignment(SwingConstants.CENTER);

        // Declare a text area field.
        JTextArea textField = new JTextArea(17, 36);
        textField.append(displayInConsole().toString());
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

    public String getParameterTime() {
        return parameterTime;
    }

    @MethodInfo(author = "TonyJ", comments = "setParameterTime", date = "2016-12-13", revision = 2)
    public void setParameterTime(String parameterTime) throws Exception {
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

    public void displayInWindowWithColouring() throws InterruptedException {
        // Declare a label field.
        JLabel labelField = new JLabel(getParameterTime());
        labelField.setHorizontalAlignment(SwingConstants.CENTER);

        // Declare a text area field.
        JTextPane textField = new JTextPane();

        // Loop through each characters and set the colour and revised shape to
        // provide a larger block.
        String printString = displayInConsole().toString().replace("  R  ", "RRRRR").replace("  Y  ", "YYYYY");
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
