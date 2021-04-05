package com.komodo.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangGJ
 * @Date 2021/04/05 11:01
 */
public class Test {

    public static void main(String[] args) throws SQLException {
        Executor.database("mysql");
        boolean insert = Executor.insert("insert into user values(?,?,?,?)", 1, "Tom", "123456",
                "2021-02-09");
        boolean delete = Executor.delete("delete from user where id = ?", 1);
        boolean update = Executor.update("update user set username = ? where id = ?", "test", 2);
        List<Map<String, String>> list = Executor.selectList("select * from user");
        System.out.println(list);
    }
}
