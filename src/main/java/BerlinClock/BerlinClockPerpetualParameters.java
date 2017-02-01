package BerlinClock;

public class BerlinClockPerpetualParameters {

    public static final int START_OF_DAY = 9;
    public static final int END_OF_DAY = 17;
    public static final int TEN_MINUTES = 10;
    public static final int ONE_HUNDRED = 100;
    private int earliestHour;
    private int latestHour;
    private int intervalBetweenDisplaysInMinutes;
    private int maximumIterationsForThisExecution;

    public BerlinClockPerpetualParameters() {
        earliestHour = START_OF_DAY;
        latestHour = END_OF_DAY;
        intervalBetweenDisplaysInMinutes = TEN_MINUTES;
        maximumIterationsForThisExecution = ONE_HUNDRED;
    }

    public BerlinClockPerpetualParameters(String[] args) {
        try {
            validateNumberOfParameters(args);
            this.earliestHour = validateEarliestHour(args[0]);
            this.latestHour = validateLatestHour(args[1]);
            this.intervalBetweenDisplaysInMinutes = validateIntervalBetweenDisplaysInMinutes(args[2]);
            this.maximumIterationsForThisExecution = validateMaximumIterationsForThisExecution(args[3]);
            validateEarliestStartHourIsNotGreaterThanLatestEndHour(earliestHour, latestHour);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void validateNumberOfParameters(String[] args) throws Exception {
        if (args.length != 4) {
            throw new Exception("Error - incorrect number of parameters.");
        }
    }

    private int validateEarliestHour(String earliestHourParameter) throws Exception {
        System.out.println("Parameter #1 (start hour) = '" + earliestHourParameter + "'");
        int earliestHour = Integer.parseInt(earliestHourParameter);
        if (earliestHour >= 0 & earliestHour <= 23) {
            return earliestHour;
        } else {
            throw new Exception("Start hour '" + earliestHourParameter + "' is invalid");
        }
    }

    private int validateLatestHour(String latestHourParameter) throws Exception {
        System.out.println("Parameter #2 (end hour)   = '" + latestHourParameter + "'");
        int latestHour = Integer.parseInt(latestHourParameter);
        if (latestHour >= 0 & latestHour <= 23) {
            return latestHour;
        } else {
            throw new Exception("End hour '" + latestHourParameter + "'is invalid");
        }
    }

    private int validateIntervalBetweenDisplaysInMinutes(String intervalBetweenDisplaysInMinutesParameter) throws Exception {
        System.out.println("Parameter #3 (interval)   = '" + intervalBetweenDisplaysInMinutesParameter + "'");
        int intervalBetweenDisplaysInMinutes = Integer.parseInt(intervalBetweenDisplaysInMinutesParameter);
        if (intervalBetweenDisplaysInMinutes >= 0 & intervalBetweenDisplaysInMinutes <= 59) {
            return intervalBetweenDisplaysInMinutes;
        } else {
            throw new Exception("Interval delay '" + intervalBetweenDisplaysInMinutesParameter + "'is invalid");
        }
    }

    private int validateMaximumIterationsForThisExecution(String maximumIterationsForThisExecutionParameter) throws Exception {
        System.out.println("Parameter #4 (iterations) = '" + maximumIterationsForThisExecutionParameter + "'");
        int maximumIterationsForThisExecution = Integer.parseInt(maximumIterationsForThisExecutionParameter);
        if (maximumIterationsForThisExecution >= 0 & maximumIterationsForThisExecution <= 1000) {
            return maximumIterationsForThisExecution;
        } else {
            throw new Exception("Maximum iterations '" + maximumIterationsForThisExecutionParameter + "'is invalid");
        }
    }

    private void validateEarliestStartHourIsNotGreaterThanLatestEndHour(int earliestStartHour, int latestEndHour) throws Exception {
        if (earliestStartHour > latestEndHour) {
            throw new Exception("Error - End hour must be greater than/equal to start hour.");
        }
    }

    public int getEarliestHour() {
        return earliestHour;
    }

    public int getLatestHour() {
        return latestHour;
    }

    public int getIntervalBetweenDisplaysInMinutes() {
        return intervalBetweenDisplaysInMinutes;
    }

    public int getMaximumIterationsForThisExecution() {
        return maximumIterationsForThisExecution;
    }
}
