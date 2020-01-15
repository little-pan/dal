package com.ctrip.platform.dal.dao.datasource.jdbc;

import com.ctrip.platform.dal.dao.datasource.SingleDataSource;
import com.ctrip.platform.dal.dao.helper.ServiceLoaderHelper;
import com.ctrip.platform.dal.dao.log.Metrics;
import com.ctrip.platform.dal.dao.log.TimeErrorLog;

import java.sql.SQLException;

public interface HandleDataSourceException {
    default void handle(SingleDataSource singleDataSource, SQLException e) throws SQLException {
        if (e != null) {
            long nowTime = System.currentTimeMillis();
            if (singleDataSource.getTimeErrorLog().getFirstErrorTime() == 0) {
                singleDataSource.setTimeErrorLog(new TimeErrorLog(nowTime, 0));
            } else {
                long duration = nowTime - singleDataSource.getTimeErrorLog().getFirstErrorTime();
                if (duration > TimeErrorLog.PERMIT_ERROR_DURATION_TIME * 1000) {
                    long lastReportErrorTime = singleDataSource.getTimeErrorLog().getFirstErrorTime();
                    if (lastReportErrorTime == 0 || nowTime - lastReportErrorTime > TimeErrorLog.REPORT_ERROR_FREQUENCY * 1000) {
                        long firstErrorTime = singleDataSource.getTimeErrorLog().getFirstErrorTime();
                        singleDataSource.setTimeErrorLog(new TimeErrorLog(firstErrorTime, nowTime));
                        Metrics metrics = ServiceLoaderHelper.getInstance(Metrics.class);
                        if (metrics != null) {
                            metrics.reportError(singleDataSource.getName());
                        }
                    }
                }
            }
            throw new SQLException(e);
        }
        else {
            singleDataSource.setTimeErrorLog(new TimeErrorLog(0, 0));
        }
    }
}
