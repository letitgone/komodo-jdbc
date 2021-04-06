package com.komodo.jdbc.configuration;

import com.komodo.jdbc.pojo.Configuration;
import com.komodo.jdbc.pojo.ConnectionInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.Arrays;

/**
 * @Author ZhangGJ
 * @Date 2021/04/03 11:49
 */
@Slf4j
public class KmdDriver {

    public static PreparedStatement getPreparedStatement(Connection connection,
            Configuration configuration, String sql, Object... args)
            throws SQLException, ClassNotFoundException {
        ConnectionInfo connectionInfo = configuration.getConnectionInfo();
        Class.forName(connectionInfo.getDriver());
        connection = DriverManager
                .getConnection(connectionInfo.getUrl(), connectionInfo.getUsername(),
                        connectionInfo.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setPreparedStatement(preparedStatement, args);
        ResultSetMetaData metaData = preparedStatement.getMetaData();
        log.info("=========================SQL: " + preparedStatement.toString());
        return preparedStatement;
    }

    public static PreparedStatement getPreparedStatement(Connection connection,
            Configuration configuration, String sql, Object[]... args)
            throws SQLException, ClassNotFoundException {
        ConnectionInfo connectionInfo = configuration.getConnectionInfo();
        Class.forName(connectionInfo.getDriver());
        connection = DriverManager
                .getConnection(connectionInfo.getUrl(), connectionInfo.getUsername(),
                        connectionInfo.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (Object[] batchSql : args) {
            setPreparedStatement(preparedStatement, batchSql);
            preparedStatement.addBatch();
            log.info("===================SQL: " + preparedStatement.toString());
        }
        return preparedStatement;
    }

    private static void setPreparedStatement(PreparedStatement preparedStatement, Object[] args)
            throws SQLException {
        for (int i = 1; i <= args.length; i++) {
            Object o = args[i - 1];
            if (o instanceof String) {
                preparedStatement.setString(i, (String) o);
            }
            if (o instanceof Integer) {
                preparedStatement.setInt(i, (Integer) o);
            }
            if (o instanceof Long) {
                preparedStatement.setLong(i, (Long) o);
            }
            if (o instanceof Boolean) {
                preparedStatement.setBoolean(i, (Boolean) o);
            }
        }
    }
}
