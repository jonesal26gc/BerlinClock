import org.junit.runners.JUnit4;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xm39 on 09/12/2016.
 */
public class BerlinClockApp {

    public static void main(String[] args) {
        /*********************************************************************************
         * Run the Berlin Clock application.
         *********************************************************************************/

        BerlinClock b = new BerlinClock();

        try {
            // If the parameters have not provided a time string, then use the current time.
            if (args[0].equals("")) {
                DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Calendar cal1 = Calendar.getInstance();
                b.setCurrentTime(sdf.format(cal1.getTime()));
            } else {
                b.setCurrentTime(args[0]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Display the time that has been used.
        System.out.println(b.toString());

        // Format and display the clock.
        b.setBerlinClock();
        b.displayBerlinClock();

        BerlinClock b2 = new BerlinClock("00:00:01");
        System.out.println(b2.toString());



    }
}
