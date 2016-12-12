import org.junit.*;

import java.util.Arrays;

import static org.junit.Assert.*;

public class BerlinClockTest {
    /*********************************************************************************
     * All test cases for the Berlin Clock.
     *********************************************************************************/
    private static final String NEW_LINE = "\n";

    @Test
    public void should_abend() {
        BerlinClock b = new BerlinClock("a");
        System.out.println(b.toString());
        assertNull(b.getCurrentTime());
    }

    @Test
    public void should_show_default() {
        BerlinClock b = new BerlinClock("");
        System.out.println(b.toString());
    }

    @Test
    public void should_show_000000() {
        BerlinClock b = new BerlinClock("00:00:00");
        System.out.println(b.toString());
        assertEquals("Incorrect time","00:00:00",b.getCurrentTime());
        assertEquals("Second should be on", Boolean.TRUE, b.getIndSecondInterval());
        assertArrayEquals("5hr should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd5HrIntervals());
        assertArrayEquals("1hr should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd1HrIntervals());
        assertArrayEquals("5min should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                                ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                                ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd5MinIntervals());
        assertArrayEquals("1min should all be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd1MinIntervals());
    }

    @Test
    public void should_show_060601() {
        BerlinClock b = new BerlinClock("06:06:01");
        System.out.println(b.toString());
        assertEquals("Incorrect","06:06:01",b.getCurrentTime());
        assertEquals("Second should be off", Boolean.FALSE, b.getIndSecondInterval());
        assertArrayEquals("First 5hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd5HrIntervals());
        assertArrayEquals("First 1hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd1HrIntervals());
        assertArrayEquals("First 5min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd5MinIntervals());
        assertArrayEquals("First 1min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd1MinIntervals());
    }

    @Test
    public void should_show_090400() {
        BerlinClock b = new BerlinClock("09:04:00");
        System.out.println(b.toString());
        assertEquals("Incorrect","09:04:00",b.getCurrentTime());
        assertEquals("Second should be on", Boolean.TRUE, b.getIndSecondInterval());
        assertArrayEquals("First 5hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd5HrIntervals());
        assertArrayEquals("All 1hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getInd1HrIntervals());
        assertArrayEquals("All 5min should be false"
                ,new boolean[] {Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE
                        ,Boolean.FALSE,Boolean.FALSE,Boolean.FALSE}
                ,b.getInd5MinIntervals());
        assertArrayEquals("All 1min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getInd1MinIntervals());
    }


    @Test
    public void should_show_235959() {
        BerlinClock b = new BerlinClock("23:59:59");
        System.out.println(b.toString());
        assertEquals("Incorrect","23:59:59",b.getCurrentTime());
        assertEquals("Second should be off", Boolean.FALSE, b.getIndSecondInterval());
        assertArrayEquals("All 5hr should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getInd5HrIntervals());
        assertArrayEquals("All 1hr should be true except the last"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.FALSE}
                ,b.getInd1HrIntervals());
        assertArrayEquals("All 5min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getInd5MinIntervals());
        assertArrayEquals("All 1min should be true"
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}
                ,b.getInd1MinIntervals());
    }

    @Test
    public void should_display_second_on() {
        BerlinClock b1 = new BerlinClock("01:00:00");
        assertEquals("Second should be displayed"
                , new String (
                 "                 * *" + NEW_LINE
                +"               *     *" + NEW_LINE
                +"             *    "+"Y"+"    *" + NEW_LINE
                +"               *     *" + NEW_LINE
                +"                 * *")
                ,b1.formatSecondBox());
    }

    @Test
    public void should_display_all_5hr_lights_on() {
        assertEquals("All four 5hr lights should be ON."
                , new String (
                        "╔═══════╗╔═══════╗╔═══════╗╔═══════╗" + NEW_LINE +
                        "║   R   ║║   R   ║║   R   ║║   R   ║" + NEW_LINE +
                        "╚═══════╝╚═══════╝╚═══════╝╚═══════╝"
                )
                ,new BerlinClock().formatFourBoxes(Boolean.TRUE
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}));
    }

    @Test
    public void should_display_all_1hr_lights_on() {
        assertEquals("All four 1hr lights should be ON."
                , new String (
                        "╔═══════╗╔═══════╗╔═══════╗╔═══════╗" + NEW_LINE +
                        "║   R   ║║   R   ║║   R   ║║   R   ║" + NEW_LINE +
                        "╚═══════╝╚═══════╝╚═══════╝╚═══════╝"
                )
                ,new BerlinClock().formatFourBoxes(Boolean.TRUE
                ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}));
    }

    @Test
    public void should_display_all_5min_lights_on() {
        assertEquals("All four 5min lights should be ON."
                , new String (
                        "╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗╔═╗ ╔═╗╔═╗" + NEW_LINE +
                        "║Y║║Y║║R║ ║Y║║Y║║R║ ║Y║║Y║║R║ ║Y║║Y║" + NEW_LINE +
                        "╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝╚═╝ ╚═╝╚═╝"
                )
                ,new BerlinClock().formatElevenBoxes(new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE
                        ,Boolean.TRUE,Boolean.TRUE}));
    }

    @Test
    public void should_display_all_1min_lights_on() {
        assertEquals("All four 1hr lights should be ON."
                , new String (
                        "╔═══════╗╔═══════╗╔═══════╗╔═══════╗" + NEW_LINE +
                        "║   Y   ║║   Y   ║║   Y   ║║   Y   ║" + NEW_LINE +
                        "╚═══════╝╚═══════╝╚═══════╝╚═══════╝"
                )
                ,new BerlinClock().formatFourBoxes(Boolean.FALSE
                        ,new boolean[] {Boolean.TRUE,Boolean.TRUE,Boolean.TRUE,Boolean.TRUE}));
    }

}
