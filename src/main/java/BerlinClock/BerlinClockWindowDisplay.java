package BerlinClock;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class BerlinClockWindowDisplay {

    private static final String[] fontOptions = {"Serif", "Agency FB", "Arial", "Calibri", "Cambrian"
            , "Century Gothic", "Comic Sans MS", "Courier New"
            , "Forte", "Garamond", "Monospaced", "Segoe UI"
            , "Times New Roman", "Trebuchet MS", "Serif"};
    private static final int[] sizeOptions = {8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28};
    private static final int windowDisplayInMilliSeconds = 10000;

    public void displayWithLetters(String parameterTime, StringBuffer console) throws InterruptedException {
        // Declare a label field.
        JLabel labelField = new JLabel(parameterTime);
        labelField.setHorizontalAlignment(SwingConstants.CENTER);

        // Declare a text area field.
        JTextArea textField = new JTextArea(17, 36);
        textField.append(console.toString());
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

    public void displayWithColour(String parameterTime, StringBuffer console) throws InterruptedException {
        // Declare a label field.
        JLabel labelField = new JLabel(parameterTime);
        labelField.setHorizontalAlignment(SwingConstants.CENTER);

        // Declare a text area field.
        JTextPane textField = new JTextPane();

        // Loop through each characters and set the colour and revised shape to
        // provide a larger block.
        String printString = console.toString().replace("  R  ", "RRRRR").replace("  Y  ", "YYYYY");
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
}
