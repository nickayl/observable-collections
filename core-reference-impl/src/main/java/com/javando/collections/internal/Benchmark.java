package com.javando.collections.internal;

import java.util.concurrent.TimeUnit;


public class Benchmark {

    private double startTime;
    private double endTime;
    // private static final Logger log = LoggerFactory.getLogger(Benchmark.class);

    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;


    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public void logResults() {
        //log.info("* =============  =============  =============  =============  =============  =============  =============  =============  =============  ============= *");
        //log.info(String.format("* ===== Benchmark results (TimeUnit: %s) ==== **** ADD OP **** start: %.0fs,  end: %.0fs,    diff: %.6fs",timeUnit.name(), getStartTime(),getEndTime(),getTimeDiff()));
        //log.info("* =============  =============  =============  =============  =============  =============  =============  =============  =============  ============= *");
        System.out.println("* =============  =============  =============  =============  =============  =============  =============  =============  =============  ============= *");
        System.out.format("* ===== Benchmark results (TimeUnit: %s) ==== **** ADD OP **** start: %.0fs,  end: %.0fs,    diff: %.6fs", timeUnit.name(), getStartTime(), getEndTime(), getTimeDiff());
        System.out.format("* =============  =============  =============  =============  =============  =============  =============  =============  =============  ============= *");
    }

    public double getTimeDiff(TimeUnit timeUnit) {
        return getEndTime(timeUnit) - getStartTime(timeUnit);
    }

    public double getStartTime(TimeUnit timeUnit) {
        return startTime / getConversionRate(timeUnit);
    }

    public double getEndTime(TimeUnit timeUnit) {
        return endTime / getConversionRate(timeUnit);
    }

    public double getTimeDiff() {
        return getTimeDiff(timeUnit);
    }

    public double getStartTime() {
        return getStartTime(timeUnit);
    }

    public double getEndTime() {
        return getEndTime(timeUnit);
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    private long getConversionRate(TimeUnit timeUnit) {
        switch (timeUnit) {
            case NANOSECONDS:
                return 1;
            case MICROSECONDS:
                return 1000;
            case MILLISECONDS:
                return 1000000;
            case SECONDS:
                return 1000000000;

            default:
                return 1;
        }
    }

}
