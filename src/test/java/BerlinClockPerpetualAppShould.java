import BerlinClock.BerlinClockPerpetualApp;
import org.junit.Test;

public class BerlinClockPerpetualAppShould {

    @Test
    public void
    run_the_display_clock_perpetually(){

        final String [] paramsIn = {"09","18","1","2"};
        BerlinClockPerpetualApp b = new BerlinClockPerpetualApp();
        b.main(paramsIn);

    }

}
