package com.komodo.jdbc.configuration;

import com.komodo.jdbc.pojo.Configuration;
import com.komodo.jdbc.pojo.ConnectionInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

/**
 * @Author ZhangGJ
 * @Date 2021/04/03 11:49
 */
@Slf4j
public class Connection {

    public static PreparedStatement getConnection(PreparedStatement preparedStatement,
            java.sql.Connection connection, Configuration configuration, String sql, Object... args)
            throws SQLException, ClassNotFoundException {
        ConnectionInfo connectionInfo = configuration.getConnectionInfo();
        Class.forName(connectionInfo.getDriver());
        connection = DriverManager
                .getConnection(connectionInfo.getUrl(), connectionInfo.getUsername(),
                        connectionInfo.getPassword());
        preparedStatement = connection.prepareStatement(sql);
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
        log.info("===================: " + preparedStatement.toString());
        return preparedStatement;
    }
}
