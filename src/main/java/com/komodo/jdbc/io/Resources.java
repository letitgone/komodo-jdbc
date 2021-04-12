package com.komodo.jdbc.io;

import java.io.InputStream;

/**
 * @Author ZhangGJ
 * @Date 2021/04/08 22:21
 */
public class Resources {

    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
