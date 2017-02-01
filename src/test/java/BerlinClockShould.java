import BerlinClock.BerlinClock;
import BerlinClock.BerlinClockWindowDisplay;
import org.junit.*;

import static org.junit.Assert.*;

public class BerlinClockShould {
    /*********************************************************************************
     * All test cases for the Berlin Clock.
     *********************************************************************************/
    private static final String NEW_LINE = "\n";

    @Test
    public void
    abend_and_return_null() {
        BerlinClock b = new BerlinClock("a");
        System.out.println(b.toString());
        assertNull(b.getParameterTime());
    }

    @Test
    public void
    show_default_time() {
        BerlinClock b = new BerlinClock("");
        System.out.println(b.toString());
    }

    @Test
    public void
    show_000000() {
        BerlinClock b = new BerlinClock("00:00:00");
        System.out.println(b.toString());
        assertEquals("Incorrect time","00:00:00",b.getParameterTime());
        assertEquals("Second should be on", Boolean.FALSE, b.getLampOnSecondIndicator());
        assertArrayEquals("5hr should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn5HourIndicators());
        assertArrayEquals("1hr should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn1hourIndicators());
        assertArrayEquals("5min should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                                ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                                ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn5MinuteIndicators());
        assertArrayEquals("1min should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn1MinuteIndicators());
    }

    @Test
    public void
    show_060601() {
        BerlinClock b = new BerlinClock("06:06:01");
        System.out.println(b.toString());
        assertEquals("Incorrect","06:06:01",b.getParameterTime());
        assertEquals("Second should be on", Boolean.TRUE, b.getLampOnSecondIndicator());
        assertArrayEquals("First 5hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn5HourIndicators());
        assertArrayEquals("First 1hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn1hourIndicators());
        assertArrayEquals("First 5min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn5MinuteIndicators());
        assertArrayEquals("First 1min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn1MinuteIndicators());
    }

    @Test
    public void
    show_090400() {
        BerlinClock b = new BerlinClock("09:04:00");
        System.out.println(b.toString());
        assertEquals("Incorrect","09:04:00",b.getParameterTime());
        assertEquals("Second should be off", Boolean.FALSE, b.getLampOnSecondIndicator());
        assertArrayEquals("First 5hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn5HourIndicators());
        assertArrayEquals("All 1hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getLampOn1hourIndicators());
        assertArrayEquals("All 5min should be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn5MinuteIndicators());
        assertArrayEquals("All 1min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getLampOn1MinuteIndicators());
    }

    @Test
    public void
    show_123200() {
        BerlinClock b = new BerlinClock("12:32:00");
        System.out.println(b.toString());
        assertEquals("Incorrect","12:32:00",b.getParameterTime());
        assertEquals("Second should be off", Boolean.FALSE, b.getLampOnSecondIndicator());
        assertArrayEquals("First/second 5hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn5HourIndicators());
        assertArrayEquals("First/second 1hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn1hourIndicators());
        assertArrayEquals("Six 5min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn5MinuteIndicators());
        assertArrayEquals("All 1min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn1MinuteIndicators());
    }

    @Test
    public void
    show_235959() {
        BerlinClock b = new BerlinClock("23:59:59");
        System.out.println(b.toString());
        assertEquals("Incorrect","23:59:59",b.getParameterTime());
        assertEquals("Second should be on", Boolean.TRUE, b.getLampOnSecondIndicator());
        assertArrayEquals("All 5hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getLampOn5HourIndicators());
        assertArrayEquals("All 1hr should be true except the last"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.FALSE}
                ,b.getLampOn1hourIndicators());
        assertArrayEquals("All 5min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getLampOn5MinuteIndicators());
        assertArrayEquals("All 1min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getLampOn1MinuteIndicators());
    }

    @Test
    public void
    show_240000() {
        BerlinClock b = new BerlinClock("24:00:00");
        System.out.println(b.toString());
        assertEquals("Incorrect time","24:00:00",b.getParameterTime());
        assertEquals("Second should be on", Boolean.FALSE, b.getLampOnSecondIndicator());
        assertArrayEquals("5hr should all be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getLampOn5HourIndicators());
        assertArrayEquals("1hr should all be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getLampOn1hourIndicators());
        assertArrayEquals("5min should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn5MinuteIndicators());
        assertArrayEquals("1min should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getLampOn1MinuteIndicators());
    }

    @Test
    public void
    display_second_on() {
        assertEquals("Second should be displayed"
                , ( NEW_LINE + "                 * *" + NEW_LINE +
                    "               *     *" + NEW_LINE +
                    "             *    "+"Y"+"    *" + NEW_LINE +
                    "               *     *" + NEW_LINE +
                    "                 * *")
                ,new BerlinClock().buildBoxForSecondDisplay(Boolean.TRUE).toString());
    }

    @Test
    public void
    display_second_off() {
        assertEquals("Second should be displayed"
                , ( NEW_LINE + "                 * *" + NEW_LINE +
                        "               *     *" + NEW_LINE +
                        "             *    "+" "+"    *" + NEW_LINE +
                        "               *     *" + NEW_LINE +
                        "                 * *")
                ,new BerlinClock().buildBoxForSecondDisplay(Boolean.FALSE).toString());
    }

    @Test
    public void
    display_all_5hr_lights_on() {
        assertEquals("All four 5hr lights should be ON."
                , ( NEW_LINE +
                    "╔═══════╗╔═══════╗╔═══════╗╔═══════╗" + NEW_LINE +
                    "║   R   ║║   R   ║║   R   ║║   R   ║" + NEW_LINE +
                    "╚═══════╝╚═══════╝╚═══════╝╚═══════╝")
                ,new BerlinClock().buildBoxesForFourBoxDisplay(new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}, Boolean.TRUE
                ).toString());
    }

    @Test
    public void
    display_all_1hr_lights_on() {
        assertEquals("All four 1hr lights should be ON."
                , ( NEW_LINE +
                    "╔═══════╗╔═══════╗╔═══════╗╔═══════╗" + NEW_LINE +
                    "║   R   ║║   R   ║║   R   ║║   R   ║" + NEW_LINE +
                    "╚═══════╝╚═══════╝╚═══════╝╚═══════╝")
                ,new BerlinClock().buildBoxesForFourBoxDisplay(new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}, Boolean.TRUE
                ).toString());
    }

    @Test
    public void
    display_all_5min_lights_on() {
        assertEquals("All four 5min lights should be ON."
                , ( NEW_LINE +
                    "╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗" + NEW_LINE +
                    "║Y║║Y║║R║ ║Y║║Y║║R║ ║Y║║Y║║R║ ║Y║║Y║" + NEW_LINE +
                    "╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝")
                ,new BerlinClock().buildBoxesForFiveMinuteDisplay(new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE}).toString());
    }

    @Test
    public void
    display_all_1min_lights_on() {
        assertEquals("All four 1hr lights should be ON."
                , ( NEW_LINE +
                    "╔═══════╗╔═══════╗╔═══════╗╔═══════╗" + NEW_LINE +
                    "║   Y   ║║   Y   ║║   Y   ║║   Y   ║" + NEW_LINE +
                    "╚═══════╝╚═══════╝╚═══════╝╚═══════╝")
                ,new BerlinClock().buildBoxesForFourBoxDisplay(new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}, Boolean.FALSE
                ).toString());
    }

    @Test
    public void
    display_200000_on_console() {
        BerlinClock b = new BerlinClock("20:00:00");
        StringBuffer console = new StringBuffer();
        b.display(console);
        System.out.println(console.toString());
    }


}
