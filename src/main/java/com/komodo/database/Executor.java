package com.komodo.database;

import com.komodo.database.pojo.Configuration;
import com.komodo.database.pojo.DataBaseInfo;
import com.komodo.util.YamlUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author ZhangGJ
 * @Date 2021/04/03 11:47
 */
public class Executor {

    private static String database;

    public static void database(String database) {
        Executor.database = database;
    }

    public static <T> T insert(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(database);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        if (o instanceof Boolean) {
            return (T) o;
        } else {
            return null;
        }
    }

    public static <T> T insertBatch(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(database);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        if (o instanceof Boolean) {
            return (T) o;
        } else {
            return null;
        }
    }

    public static <T> T delete(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(database);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        if (o instanceof Boolean) {
            return (T) o;
        } else {
            return null;
        }
    }

    public static <T> T deleteBatch(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(database);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        if (o instanceof Boolean) {
            return (T) o;
        } else {
            return null;
        }
    }

    public static <T> T update(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(database);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        if (o instanceof Boolean) {
            return (T) o;
        } else {
            return null;
        }
    }

    public static <T> T selectOne(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(database);
        ResultSet resultSet = Connection.getConnection(configuration, sql, args).executeQuery();
        while (resultSet.next()) {
        }
        return null;
    }

    public static <E> List<E> selectList(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(database);
        ResultSet resultSet = Connection.getConnection(configuration, sql, args).executeQuery();
        while (resultSet.next()) {
        }
        return null;
    }

    private static Configuration configuration(String database) {
        DataBaseInfo dataBaseInfo = YamlUtil.readYaml(database, DataBaseInfo.class);
        Configuration configuration = new Configuration();
        configuration.setDataBaseInfo(dataBaseInfo);
        return configuration;
    }

}
