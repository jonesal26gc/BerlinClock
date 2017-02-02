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
    default_without_parameters() throws Exception {
        String[] parametersInput = {};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);
    }

    @Test
    public void
    error_with_too_few_parameters() throws Exception {
        // given
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Error - incorrect number of parameters.");

        // when
        String[] parametersInput = {"1"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);
    }

    @Test
    public void
    run_with_4_valid_parameters() throws Exception {
        //When
        String[] parametersInput = {"01", "23", "4", "5"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);

        // then
        assertThat(berlinClockPerpetualParameters.getEarliestHour(), is(1));
        assertThat(berlinClockPerpetualParameters.getLatestHour(), is(23));
        assertThat(berlinClockPerpetualParameters.getIntervalBetweenDisplaysInMinutes(), is(4));
        assertThat(berlinClockPerpetualParameters.getMaximumIterationsForThisExecution(), is(5));
    }

    //@Test(expected = Exception.class)
    @Test
    public void
    error_with_earliestHour_of_24() throws Exception {
        // given
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Earliest hour '24' is invalid.");

        // when
        String[] parametersInput = {"24", "23", "1", "1"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);
    }

    @Test
    public void
    error_with_latestHour_of_24() throws Exception {
        // given
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Latest hour '24' is invalid.");

        // when
        String[] parametersInput = {"23", "24", "1", "1"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);
    }

    @Test
    public void
    error_with_earliestHour_13_and_latestHour_of_12() throws Exception {
        // given
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Error - Latest hour must be greater than/equal to Earliest hour.");

        // when
        String[] parametersInput = {"13", "12", "1", "1"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);
    }

    @Test
    public void
    error_with_interval_of_0() throws Exception {
        // given
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Interval delay '0' is invalid.");

        // when
        String[] parametersInput = {"09", "17", "0", "1"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);
    }

    @Test
    public void
    error_with_interval_of_60() throws Exception {
        // given
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Interval delay '60' is invalid.");

        // when
        String[] parametersInput = {"09", "17", "60", "1"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);
    }

    @Test
    public void
    error_with_iterations_of_0() throws Exception {
        // given
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Maximum iterations '0' is invalid.");

        // when
        String[] parametersInput = {"09", "17", "1", "0"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);
    }

    @Test
    public void
    error_with_iterations_of_1001() throws Exception {
        // given
        thrown.expect(java.lang.Exception.class);
        thrown.expectMessage("Maximum iterations '1001' is invalid.");

        // when
        String[] parametersInput = {"09", "17", "1", "1001"};
        BerlinClockPerpetualParameters berlinClockPerpetualParameters =
                new BerlinClockPerpetualParameters(parametersInput);
    }
}
