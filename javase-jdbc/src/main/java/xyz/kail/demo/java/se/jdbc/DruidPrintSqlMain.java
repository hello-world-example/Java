package xyz.kail.demo.java.se.jdbc;

import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Properties;

public class DruidPrintSqlMain {


    static {
        // slf4j-simple 配置
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");

        // logback-classic 配置
//        Logger logger = LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
//        if (logger instanceof ch.qos.logback.classic.Logger) {
//            ch.qos.logback.classic.Logger logback = (ch.qos.logback.classic.Logger) logger;
//            // 设置日志级别
//            logback.setLevel(Level.DEBUG);
//            LoggerContext loggerContext = logback.getLoggerContext();
//
//            Appender<ILoggingEvent> appender = logback.getAppender("console");
//            if (appender instanceof ConsoleAppender) {
//                ConsoleAppender<ILoggingEvent> consoleAppender = (ConsoleAppender<ILoggingEvent>) appender;
//
//                PatternLayoutEncoder patternLayout = new PatternLayoutEncoder();
//                // https://logback.qos.ch/manual/layouts.html#conversionWord
//                patternLayout.setPattern("%d{yyyy-MM-dd HH:mm:ss} [%p][%C][%c][%M][%L]-> %m%n");
//                patternLayout.setContext(loggerContext);
//                patternLayout.start();
//
//                consoleAppender.setEncoder(patternLayout);
//            }
//            appender.setContext(loggerContext);
//            appender.start();
//        }
//
//        ch.qos.logback.classic.Logger logback = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.alibaba");
//        logback.setLevel(Level.OFF);
//        // false：表示只用当前logger的appender-ref。
//        // true：表示当前logger的appender-ref和rootLogger的appender-ref都有效。
//        logback.setAdditive(false);

    }

    public static void main(String[] args) throws Exception {

        // logger name : druid.sql.Statement
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        // 是否 打印Connection 日志
        slf4jLogFilter.setConnectionLogEnabled(false); // 默认 true


        // Statement 日志
        slf4jLogFilter.setStatementLogEnabled(true);  // 默认 true
        // 是否 打印PreparedStatement语句
        slf4jLogFilter.setStatementPrepareAfterLogEnabled(false);  // 默认 true
        // 是否 打印PreparedStatement语句参数和参数类型
        slf4jLogFilter.setStatementParameterSetLogEnabled(false);  // 默认 true
        // ❤❤❤ 是否 打印Statement可执行语句
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);  // 默认 false
        // ❤❤❤ 是否 打印执行耗时
        slf4jLogFilter.setStatementExecuteQueryAfterLogEnabled(false); // 默认 true
        // 是否 打印 Statement close 日志
        slf4jLogFilter.setStatementCloseAfterLogEnabled(false); // 默认 true

        // 是否 打印 执行结果
        slf4jLogFilter.setResultSetLogEnabled(false);  // 默认 true


        Properties config = new Properties();
        // Loading class `com.mysql.jdbc.Driver'. This is deprecated.
        // The new driver class is `com.mysql.cj.jdbc.Driver'.
        // The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
        // config.setProperty(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, "com.mysql.jdbc.Driver");
        config.setProperty(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, "com.mysql.cj.jdbc.Driver");
        config.setProperty(DruidDataSourceFactory.PROP_URL, "jdbc:mysql:///test");
        config.setProperty(DruidDataSourceFactory.PROP_USERNAME, "root");
        config.setProperty(DruidDataSourceFactory.PROP_PASSWORD, "1723");

        DruidDataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(config);
        // 设置过滤器
        dataSource.setProxyFilters(Collections.singletonList(slf4jLogFilter));


        Connection conn = dataSource.getConnection();

        PreparedStatement preparedStatement = conn.prepareStatement("select * from T_TEST_ where ID = ?");
        preparedStatement.setLong(1, 613290L);

        ResultSet resultSet = preparedStatement.executeQuery();

        JdbcUtils.close(resultSet);
        JdbcUtils.close(preparedStatement);
        JdbcUtils.close(conn);

    }

}
