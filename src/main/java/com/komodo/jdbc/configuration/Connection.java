package com.komodo.jdbc.configuration;

import com.komodo.jdbc.pojo.Configuration;
import com.komodo.jdbc.pojo.ConnectionInfo;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author ZhangGJ
 * @Date 2021/04/03 11:49
 */
public class Connection {

    public static PreparedStatement getConnection(Configuration configuration, String sql,
            Object... args) {
        java.sql.Connection connection = null;
        PreparedStatement preparedStatement;
        ConnectionInfo connectionInfo = configuration.getConnectionInfo();
        try {
            String driver = connectionInfo.getDriver();
            Class.forName(driver);
            connection = DriverManager
                    .getConnection(connectionInfo.getUrl(), connectionInfo.getUsername(),
                            connectionInfo.getPassword());
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= args.length; i++) {
                Object o = args[i];
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
            return preparedStatement;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }
}
