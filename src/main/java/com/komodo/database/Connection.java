package com.komodo.database;

import com.komodo.database.pojo.Configuration;
import com.komodo.database.pojo.DataBaseInfo;

import java.sql.*;

/**
 * @Author ZhangGJ
 * @Date 2021/04/03 11:49
 */
public class Connection {

    public static PreparedStatement getConnection(Configuration configuration, String sql,
            Object... args) {
        java.sql.Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        DataBaseInfo dataBaseInfo = configuration.getDataBaseInfo();
        try {
            String driver = dataBaseInfo.getDriver();
            Class.forName(driver);
            connection = DriverManager
                    .getConnection(dataBaseInfo.getUrl(), dataBaseInfo.getUsername(),
                            dataBaseInfo.getPassword());
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
