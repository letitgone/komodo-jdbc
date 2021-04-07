package com.komodo.jdbc.configuration;

import com.alibaba.fastjson.JSONObject;
import com.komodo.jdbc.pojo.Configuration;
import com.komodo.jdbc.pojo.ConnectionInfo;
import com.komodo.jdbc.pojo.DatasourceEnum;
import com.komodo.jdbc.utils.YamlUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangGJ
 * @Date 2021/04/03 11:47
 */
@Slf4j
public class Executor {

    private Connection connection;

    private final Configuration configuration;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;

    public Executor(DatasourceEnum datasourceEnum) {
        configuration = configuration(datasourceEnum);
    }

    public <T> T insert(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    public <T> T insertBatch(String sql, Object[]... args) {
        return executeBatch(sql, args);
    }

    public <T> T delete(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    public <T> T update(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    public <T> T selectOne(Class<T> clazz, String sql, Object... args) {
        return executeOne(clazz, sql, args);
    }

    public <T> Map<String, Object> selectMapOne(Class<T> clazz, String sql, Object... args) {
        return executeMapOne(clazz, sql, args);
    }

    public <E> List<E> selectList(Class<E> clazz, String sql, Object... args) {
        return executeSelect(clazz, sql, args);
    }

    public <E> List<Map<String, Object>> selectMap(Class<E> clazz, String sql, Object... args) {
        return executeMap(clazz, sql, args);
    }

    private <T> T executeBatch(String sql, Object[]... args) {
        Object o = null;
        try {
            preparedStatement = KmdDriver.getPreparedStatement(connection, configuration, sql, args);
            o = preparedStatement.executeBatch();
            int[] resultRow = (int[]) o;
            int rows = 0;
            for (int row : resultRow) {
                rows += row;
            }
            log.info("=====================UPDATE: " + rows);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.commit();
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return (T) o;
    }

    private <T> T executeUpdate(String sql, Object... args) {
        Object o = null;
        try {
            preparedStatement = KmdDriver.getPreparedStatement(connection, configuration, sql, args);
            o = preparedStatement.executeUpdate();
            log.info("===========================UPDATE: " + (int) o);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.commit();
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return (T) o;
    }

    private <T> Map<String, Object> executeMapOne(Class<T> clazz, String sql, Object... args) {
        try {
            preparedStatement = KmdDriver.getPreparedStatement(connection, configuration, sql, args);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            Map<String, Object> data;
            data = new HashMap<>(columns);
            for (int i = 1; i <= columns; i++) {
                String key = metaData.getColumnName(i);
                data.put(key, resultSet.getObject(key));
            }
            return data;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.commit();
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return null;
    }

    private <E> List<Map<String, Object>> executeMap(Class<E> clazz, String sql, Object... args) {
        try {
            preparedStatement = KmdDriver.getPreparedStatement(connection, configuration, sql, args);
            resultSet = preparedStatement.executeQuery();
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
            if (connection != null) {
                try {
                    connection.commit();
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return null;
    }

    private <E> List<E> executeSelect(Class<E> clazz, String sql, Object... args) {
        try {
            preparedStatement = KmdDriver.getPreparedStatement(connection, configuration, sql, args);
            resultSet = preparedStatement.executeQuery();
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
            if (connection != null) {
                try {
                    connection.commit();
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return null;
    }

    private <T> T executeOne(Class<T> clazz, String sql, Object... args) {
        ResultSet resultSet;
        try {
            preparedStatement = KmdDriver.getPreparedStatement(connection, configuration, sql, args);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            JSONObject data;
            data = new JSONObject();
            for (int i = 1; i <= columns; i++) {
                String key = metaData.getColumnName(i);
                data.put(key, resultSet.getObject(key));
            }
            return JSONObject.toJavaObject(data, clazz);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.commit();
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return null;
    }

    private Configuration configuration(DatasourceEnum datasourceEnum) {
        ConnectionInfo connectionInfo =
                YamlUtil.readYaml(datasourceEnum.toString(), ConnectionInfo.class);
        Configuration configuration = new Configuration();
        configuration.setConnectionInfo(connectionInfo);
        return configuration;
    }

}
