package com.ctrip.platform.dal.dao.datasource.jdbc;

import com.ctrip.platform.dal.dao.configure.DataSourceConfigure;
import com.ctrip.platform.dal.dao.datasource.RefreshableDataSource;
import com.ctrip.platform.dal.dao.log.TimeErrorLog;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static com.ctrip.platform.dal.dao.configure.DataSourceConfigureConstants.*;
import static com.ctrip.platform.dal.dao.configure.DataSourceConfigureConstants.DRIVER_CLASS_NAME;

public class DalStatementTest {
    private Properties properties = new Properties();
    private RefreshableDataSource dataSource = null;
    private Connection connection = null;

    {
        properties.setProperty(USER_NAME, "root");
        properties.setProperty(PASSWORD, "123456");
        properties.setProperty(CONNECTION_URL, "jdbc:mysql://localhost:3306/test");
        properties.setProperty(DRIVER_CLASS_NAME, "com.mysql.jdbc.Driver");
        DataSourceConfigure configure = new DataSourceConfigure("DalService2DB_w", properties);
        try {
            dataSource = new RefreshableDataSource("DalService2DB_w", configure);
            connection = dataSource.getConnection();
        } catch (SQLException e) {

        }
    }

    @Test
    public void testStatementExecuteQuery() throws Exception {
        Statement statement = connection.createStatement();
        statement.executeQuery("select 1");
        TimeErrorLog timeErrorLog1 = dataSource.getSingleDataSource().getTimeErrorLog();
        Assert.assertEquals(timeErrorLog1.getFirstErrorTime(), 0);
        Assert.assertEquals(timeErrorLog1.getLastReportErrorTime(), 0);

        try {
            statement.executeQuery("select *from noTable");
        } catch (SQLException e) {

        }
        TimeErrorLog timeErrorLog2 = dataSource.getSingleDataSource().getTimeErrorLog();
        Assert.assertNotEquals(timeErrorLog2.getFirstErrorTime(), 0);
        Assert.assertEquals(timeErrorLog2.getLastReportErrorTime(), 0);

        Thread.sleep(1000*60);
        try {
            statement.executeQuery("select *from noTable");
        } catch (SQLException e) {

        }
        TimeErrorLog timeErrorLog3 = dataSource.getSingleDataSource().getTimeErrorLog();
        Assert.assertNotEquals(timeErrorLog3.getFirstErrorTime(), 0);
        Assert.assertNotEquals(timeErrorLog3.getLastReportErrorTime(), 0);

        try {
            statement.executeQuery("select 1");
        } catch (SQLException e) {

        }
        TimeErrorLog timeErrorLog4 = dataSource.getSingleDataSource().getTimeErrorLog();
        Assert.assertEquals(timeErrorLog4.getFirstErrorTime(), 0);
        Assert.assertEquals(timeErrorLog4.getLastReportErrorTime(), 0);
    }

    @Test
    public void testStatementExecuteUpdate() throws Exception {
        Statement statement = connection.createStatement();
//        statement.executeUpdate("update noTable set id = 1");
//        TimeErrorLog timeErrorLog1 = dataSource.getSingleDataSource().getTimeErrorLog();
//        Assert.assertEquals(timeErrorLog1.getFirstErrorTime(), 0);
//        Assert.assertEquals(timeErrorLog1.getLastReportErrorTime(), 0);

        try {
            statement.executeUpdate("update noTable set id=2");
        } catch (SQLException e) {

        }
        TimeErrorLog timeErrorLog2 = dataSource.getSingleDataSource().getTimeErrorLog();
        Assert.assertNotEquals(timeErrorLog2.getFirstErrorTime(), 0);
        Assert.assertEquals(timeErrorLog2.getLastReportErrorTime(), 0);

        Thread.sleep(1000*60);
        try {
            statement.executeQuery("select *from noTable");
        } catch (SQLException e) {

        }
        TimeErrorLog timeErrorLog3 = dataSource.getSingleDataSource().getTimeErrorLog();
        Assert.assertNotEquals(timeErrorLog3.getFirstErrorTime(), 0);
        Assert.assertNotEquals(timeErrorLog3.getLastReportErrorTime(), 0);

        try {
            statement.execute("select 1");
        } catch (SQLException e) {

        }
        TimeErrorLog timeErrorLog4 = dataSource.getSingleDataSource().getTimeErrorLog();
        Assert.assertEquals(timeErrorLog4.getFirstErrorTime(), 0);
        Assert.assertEquals(timeErrorLog4.getLastReportErrorTime(), 0);
    }
}
