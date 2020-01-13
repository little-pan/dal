package com.ctrip.platform.dal.dao.log;

public class TimeErrorLog {
    public static final int PERMIT_ERROR_DURATION_TIME = 60; //second
    public static final int REPORT_ERROR_FREQUENCY = 30; //second

    private long firstErrorTime = 0;

    private long lastReportErrorTime = 0;

    public TimeErrorLog(long firstErrorTime, long lastReportErrorTime) {
        this.firstErrorTime = firstErrorTime;
        this.lastReportErrorTime = lastReportErrorTime;
    }

    public TimeErrorLog() {}

    public long getFirstErrorTime() {
        return firstErrorTime;
    }

    public void setFirstErrorTime(long firstErrorTime) {
        this.firstErrorTime = firstErrorTime;
    }

    public long getLastReportErrorTime() {
        return lastReportErrorTime;
    }

    public void setLastReportErrorTime(long lastReportErrorTime) {
        this.lastReportErrorTime = lastReportErrorTime;
    }
}
