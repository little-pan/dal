package com.ctrip.platform.dal.dao.log;

public class DefaultMetrics implements Metrics {
    @Override
    public void reportError(String name) {

    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
