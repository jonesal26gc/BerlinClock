import org.junit.*;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BerlinClockTest {
    /*********************************************************************************
     * All test cases for the Berlin Clock.
     *********************************************************************************/

    @Test
    public void should_return_valid_time() {
        BerlinClock b1 = new BerlinClock("12:06:01");
        assertEquals("not the same !","12:06:01",b1.getCurrentTime());
    }

    @Test
    public void should_show_second_on() {
        BerlinClock b1 = new BerlinClock("00:00:00");
        System.out.println(b1.toString());
        assertTrue("second should be on", b1.getIndSecondInterval());
    }

    @Test
    public void should_show_second_off() {
        BerlinClock b1 = new BerlinClock("00:00:01");
        assertTrue("second should be off", (! (b1.getIndSecondInterval())));
    }

    @Test
    public void should_show_5hr_on() {
        BerlinClock b1 = new BerlinClock("05:05:01");
        assertArrayEquals("5hr should be on"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b1.getInd5HrIntervals());
    }

    @Test
    public void should_show_date_error() {
        BerlinClock b1 = new BerlinClock(":05:01");
    }
}
