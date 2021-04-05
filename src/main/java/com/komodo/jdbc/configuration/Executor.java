package com.komodo.jdbc.configuration;

import com.alibaba.fastjson.JSONObject;
import com.komodo.jdbc.pojo.Configuration;
import com.komodo.jdbc.pojo.ConnectionInfo;
import com.komodo.jdbc.pojo.DatasourceEnum;
import com.komodo.jdbc.utils.YamlUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangGJ
 * @Date 2021/04/03 11:47
 */
public class Executor {

    private static java.sql.Connection connection = null;

    private static Configuration configuration;

    private static PreparedStatement preparedStatement;

    private static ResultSet resultSet;

    private static PreparedStatement preparedStatements = null;

    public static void build(DatasourceEnum datasourceEnum) {
        configuration = configuration(datasourceEnum);
    }

    public static <T> T insert(String sql, Object... args) {
        return execute(sql, args);
    }

    public static <T> T insertBatch(String sql, Object... args) {
        return execute(sql, args);
    }

    public static <T> T delete(String sql, Object... args) {
        return execute(sql, args);
    }

    public static <T> T deleteBatch(String sql, Object... args) {
        return execute(sql, args);
    }

    public static <T> T update(String sql, Object... args) {
        return execute(sql, args);
    }

    public static <T> T selectOne(Class<T> clazz, String sql, Object... args) {
        return executeOne(clazz, sql, args);
    }

    public static <T> Map<String, Object> selectMapOne(Class<T> clazz, String sql, Object... args) {
        return executeMapOne(clazz, sql, args);
    }

    public static <E> List<E> selectList(Class<E> clazz, String sql, Object... args) {
        return executeSelect(clazz, sql, args);
    }

    public static <E> List<Map<String, Object>> selectMap(Class<E> clazz, String sql,
            Object... args) {
        return executeMap(clazz, sql, args);
    }

    private static <T> T execute(String sql, Object[] args) {
        Object o = null;
        PreparedStatement preparedStatements = null;
        try {
            preparedStatements = Connection
                    .getConnection(Executor.preparedStatement, Executor.connection, configuration,
                            sql, args);
            o = preparedStatements.execute();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.commit();
                    connection.close();
                }
                preparedStatements.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return (T) o;
    }

    private static <T> Map<String, Object> executeMapOne(Class<T> clazz, String sql,
            Object[] args) {
        try {
            preparedStatements = Connection
                    .getConnection(preparedStatement, connection, configuration, sql, args);
            resultSet = preparedStatements.executeQuery();
            int row = resultSet.getRow();
            List<Map<String, Object>> result = new ArrayList<>(row);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            Map<String, Object> data = null;
            while (resultSet.next()) {
                data = new HashMap<>(columns);
                for (int i = 1; i <= columns; i++) {
                    String key = metaData.getColumnName(i);
                    data.put(key, resultSet.getObject(key));
                }
                break;
            }
            return data;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.commit();
                    connection.close();
                }
                preparedStatements.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

    private static <E> List<Map<String, Object>> executeMap(Class<E> clazz, String sql,
            Object[] args) {
        try {
            preparedStatements = Connection
                    .getConnection(preparedStatement, connection, configuration, sql, args);
            resultSet = preparedStatements.executeQuery();
            int row = resultSet.getRow();
            List<Map<String, Object>> result = new ArrayList<>(row);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> data = new HashMap<>(columns);
                for (int i = 1; i <= columns; i++) {
                    String key = metaData.getColumnName(i);
                    data.put(key, resultSet.getObject(key));
                }
                result.add(data);
            }
            return result;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.commit();
                    connection.close();
                }
                preparedStatements.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

    private static <E> List<E> executeSelect(Class<E> clazz, String sql, Object[] args) {
        try {
            preparedStatements = Connection
                    .getConnection(preparedStatement, connection, configuration, sql, args);
            resultSet = preparedStatements.executeQuery();
            int row = resultSet.getRow();
            List<E> result = new ArrayList<>(row);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            while (resultSet.next()) {
                E e;
                JSONObject data = new JSONObject();
                for (int i = 1; i <= columns; i++) {
                    String key = metaData.getColumnName(i);
                    data.put(key, resultSet.getObject(key));
                }
                e = JSONObject.toJavaObject(data, clazz);
                result.add(e);
            }
            return result;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.commit();
                    connection.close();
                }
                preparedStatements.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

    private static <T> T executeOne(Class<T> clazz, String sql, Object[] args) {
        ResultSet resultSet;
        PreparedStatement preparedStatements = null;
        try {
            preparedStatements = Connection
                    .getConnection(preparedStatement, connection, configuration, sql, args);
            resultSet = preparedStatements.executeQuery();
            int row = resultSet.getRow();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            T t;
            JSONObject data = null;
            while (resultSet.next()) {
                data = new JSONObject();
                for (int i = 1; i <= columns; i++) {
                    String key = metaData.getColumnName(i);
                    data.put(key, resultSet.getObject(key));
                }
                break;
            }
            return JSONObject.toJavaObject(data, clazz);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.commit();
                    connection.close();
                }
                preparedStatements.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
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
