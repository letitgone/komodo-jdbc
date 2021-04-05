package com.komodo.jdbc.configuration;

import com.komodo.jdbc.pojo.Configuration;
import com.komodo.jdbc.pojo.ConnectionInfo;
import com.komodo.jdbc.pojo.DatasourceEnum;
import com.komodo.jdbc.utils.YamlUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author ZhangGJ
 * @Date 2021/04/03 11:47
 */
public class Executor {

    private static DatasourceEnum datasourceEnum;

    public static void database(DatasourceEnum datasourceEnum) {
        Executor.datasourceEnum = datasourceEnum;
    }

    public static <T> T insert(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(datasourceEnum);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        return (T) o;
    }

    public static <T> T insertBatch(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(datasourceEnum);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        return (T) o;
    }

    public static <T> T delete(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(datasourceEnum);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        return (T) o;
    }

    public static <T> T deleteBatch(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(datasourceEnum);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        return (T) o;
    }

    public static <T> T update(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(datasourceEnum);
        Object o = Connection.getConnection(configuration, sql, args).execute();
        return (T) o;
    }

    public static <T> T selectOne(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(datasourceEnum);
        ResultSet resultSet = Connection.getConnection(configuration, sql, args).executeQuery();
        while (resultSet.next()) {
        }
        return null;
    }

    public static <E> List<E> selectList(String sql, Object... args) throws SQLException {
        Configuration configuration = configuration(datasourceEnum);
        ResultSet resultSet = Connection.getConnection(configuration, sql, args).executeQuery();
        while (resultSet.next()) {
        }
        return null;
    }

    private static Configuration configuration(DatasourceEnum datasourceEnum) {
        ConnectionInfo connectionInfo =
                YamlUtil.readYaml(datasourceEnum.toString(), ConnectionInfo.class);
        Configuration configuration = new Configuration();
        configuration.setConnectionInfo(connectionInfo);
        return configuration;
    }

}
