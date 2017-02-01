import BerlinClock.BerlinClockPerpetualParameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BerlinClockPerpetualParametersShould {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void
    default_without_parameters(){
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters();
    }

    @Test
    public void
    run_with_4_parameters(){
        String[] parms = {"01","23","4","5"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parms);
        assertThat(berlinClockPerpetualParameters.getEarliestHour(),is(1));
        assertThat(berlinClockPerpetualParameters.getLatestHour(),is(23));
        assertThat(berlinClockPerpetualParameters.getIntervalBetweenDisplaysInMinutes(),is(4));
        assertThat(berlinClockPerpetualParameters.getMaximumIterationsForThisExecution(),is(5));
    }

//    @Test(expected = Exception.class)
//    @Test
//    public void
//    error_with_earliestHour_of_0(){
//        thrown.expect(java.lang.Exception.class);
//        thrown.expectMessage("Start hour '24' is invalid");
//
//        String[] parms = {"24","23","4","5"};
//        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
//                new BerlinClockPerpetualParameters(parms);
//    }
}
