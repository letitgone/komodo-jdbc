package com.komodo.jdbc.configuration;

import com.komodo.community.pojo.connection.ConnectionInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author ZhangGJ
 * @Date 2021/04/08 22:11
 */
@Data
public class Configuration implements Serializable {

    private static final long serialVersionUID = 5179538644054968222L;

    private ConnectionInfo connectionInfo;

}
