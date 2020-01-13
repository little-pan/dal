package com.ctrip.platform.dal.dao.log;

import com.ctrip.platform.dal.dao.helper.Ordered;

public interface Metrics extends Ordered {
    void reportError(String name);
}
