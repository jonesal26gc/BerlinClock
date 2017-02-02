import BerlinClock.BerlinClockPerpetualApp;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

public class BerlinClockPerpetualAppShould {

    @Test
    public void
    run_the_display_clock_perpetually() throws Exception{
        final String[] paramsIn = {"09","18","1","2"};
        BerlinClockPerpetualApp berlinClockPerpetualApp = new BerlinClockPerpetualApp();
        berlinClockPerpetualApp.main(paramsIn);
    }
}
