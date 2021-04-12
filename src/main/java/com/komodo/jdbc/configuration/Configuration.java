package com.komodo.jdbc.configuration;

import com.komodo.community.pojo.ConnectionInfo;
import com.komodo.jdbc.statement.MappedStatement;
import lombok.Data;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ZhangGJ
 * @Date 2021/04/08 22:11
 */
@Data
public class Configuration implements Serializable {

    private static final long serialVersionUID = 5179538644054968222L;

    private DataSource dataSource;

    private ConnectionInfo connectionInfo;

    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>(16);
}
