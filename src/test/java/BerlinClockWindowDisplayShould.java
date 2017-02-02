import BerlinClock.BerlinClock;
import BerlinClock.BerlinClockWindowDisplay;
import org.junit.Test;

/**
 * Created by xm39 on 01/02/2017.
 */
public class BerlinClockWindowDisplayShould {

    @Test
    public void
    display_200000_in_window() {
        BerlinClock b = new BerlinClock("20:00:00");
        StringBuffer console = new StringBuffer();
        b.display(console);
        try {
            new BerlinClockWindowDisplay().displayWithLetters(b.getParameterTime(),console);
        } catch ( InterruptedException ex ) {
            ex.printStackTrace();
        }
    }

    @Test
    public void
    display_200000_in_pane() {
        BerlinClock b = new BerlinClock("20:00:00");
        StringBuffer console = new StringBuffer();
        b.display(console);
        try {
            new BerlinClockWindowDisplay().displayWithColour(b.getParameterTime(),console);
        } catch ( InterruptedException ex ) {
            ex.printStackTrace();
        }
    }
}
