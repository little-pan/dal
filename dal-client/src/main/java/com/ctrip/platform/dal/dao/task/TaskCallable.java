package com.ctrip.platform.dal.dao.task;

import com.ctrip.platform.dal.dao.client.LogEntry;

import java.util.concurrent.Callable;

public interface TaskCallable<T> extends Callable<T> {
    DalTaskContext getDalTaskContext();
    default long getStatementExecuteTime(DalTaskContext taskContext) {
        if (taskContext != null) {
            return taskContext.getStatementExecuteTime();
        }
        return 0;
    }

    default void setStatementExecuteTime(DalTaskContext taskContext, long tableStatementExecuteTime) {
        if (taskContext != null) {
            taskContext.setStatementExecuteTime(tableStatementExecuteTime);
        }
    }

    default LogEntry getLogEntry(DalTaskContext taskContext) {
        if (taskContext != null) {
            return taskContext.getLogEntry();
        }
        return null;
    }
}
