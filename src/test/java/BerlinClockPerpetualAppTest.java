import BerlinClock.BerlinClockPerpetualApp;
import org.junit.Test;

public class BerlinClockPerpetualAppTest {

    @Test
    public void shouldRunApp(){

        final String [] paramsIn = {"15","15","1","1"};
        BerlinClockPerpetualApp b = new BerlinClockPerpetualApp();
        b.main(paramsIn);

    }

}
