package BerlinClock;

public class BerlinClockPerpetualParameters {

    public static final int START_OF_DAY = 9;
    public static final int END_OF_DAY = 17;
    public static final int TEN_MINUTES = 10;
    public static final int ONE_HUNDRED = 100;
    private int earliestStartHour;
    private int latestEndHour;
    private int intervalBetweenDisplaysInMinutes;
    private int maximumIterationsForThisExecution;

    public BerlinClockPerpetualParameters() {
        earliestStartHour = START_OF_DAY;
        latestEndHour = END_OF_DAY;
        intervalBetweenDisplaysInMinutes = TEN_MINUTES;
        maximumIterationsForThisExecution = ONE_HUNDRED;
    }

    public BerlinClockPerpetualParameters(String[] args) {
        try {
            validateNumberOfParameters(args);
            this.earliestStartHour = validateEarliestStartHour(args[0]);
            this.latestEndHour = validateLatestEndHour(args[1]);
            this.intervalBetweenDisplaysInMinutes = validateIntervalBetweenDisplaysInMinutes(args[2]);
            this.maximumIterationsForThisExecution = validateMaximumIterationsForThisExecution(args[3]);
            validateEarliestStartHourIsNotGreaterThanLatestEndHour(earliestStartHour, latestEndHour);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getEarliestStartHour() {
        return earliestStartHour;
    }
    public int getLatestEndHour() {
        return latestEndHour;
    }
    public int getIntervalBetweenDisplaysInMinutes() {
        return intervalBetweenDisplaysInMinutes;
    }
    public int getMaximumIterationsForThisExecution() {
        return maximumIterationsForThisExecution;
    }

    private void validateNumberOfParameters(String[] args) throws Exception {
        if (args.length != 4) {
            throw new Exception("Error - incorrect number of parameters.");
        }
    }

    private int validateEarliestStartHour(String earliestStartHourParameter) throws Exception {
        System.out.println("Parameter #1 (start hour) = '" + earliestStartHourParameter + "'");
        int earliestStartHour = Integer.parseInt(earliestStartHourParameter);
        if (earliestStartHour >= 0 & earliestStartHour <= 23) {
            return earliestStartHour;
        } else {
            throw new Exception("Start hour '" + earliestStartHourParameter + "' is invalid");
        }
    }

    private int validateLatestEndHour(String latestEndHourParameter) throws Exception {
        System.out.println("Parameter #2 (end hour)   = '" + latestEndHourParameter + "'");
        int latestEndHour = Integer.parseInt(latestEndHourParameter);
        if (latestEndHour >= 0 & latestEndHour <= 23) {
            return latestEndHour;
        } else {
            throw new Exception("End hour '" + latestEndHourParameter + "'is invalid");
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
}
