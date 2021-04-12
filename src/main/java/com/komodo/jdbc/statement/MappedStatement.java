package com.komodo.jdbc.statement;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ZhangGJ
 * @Date 2021/04/08 22:13
 */
@Data
public class MappedStatement implements Serializable {

    private Integer id;

    private String sql;

    private Class<?> parameterType;

    private Class<?> resultType;
}
