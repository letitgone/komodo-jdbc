package com.komodo.jdbc.session;

import com.komodo.jdbc.configuration.Configuration;

import java.io.InputStream;

/**
 * @Author ZhangGJ
 * @Date 2021/04/08 22:26
 */
public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) {
        return null;
    }
}
