import com.komodo.jdbc.configuration.Executor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import pojo.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.komodo.jdbc.pojo.DatasourceEnum.MYSQL;

/**
 * @Author ZhangGJ
 * @Date 2021/04/05 11:01
 */
@Slf4j
public class JdbcTest {

    @Test
    public void insert() {
        new Executor(MYSQL)
                .insert("insert into user (username, password, birthday) values(?,?,?)", "Tomsss",
                        "123456", "2021-02-09");
    }

    @Test
    public void insertBatch() {
        String[] values = {"Tom", "123456", "2021-02-09"};
        String[] values1 = {"Tom1", "1234561", "2021-02-091"};
        String[] values2 = {"Tom2", "1234562", "2021-02-092"};
        new Executor(MYSQL)
                .insertBatch("insert into user (username, password, birthday) values(?,?,?)",
                        values, values1, values2);
    }

    @Test
    public void delete() {
        new Executor(MYSQL).delete("delete from user where id in (?)", 86);
    }

    @Test
    public void update() {
        new Executor(MYSQL).update("update user set username = ? where id = ?", "愣头青", 2);
    }

    @Test
    public void selectList() {
        List<User> userList = new Executor(MYSQL).selectList(User.class, "select * from user");
        for (User user : userList) {
            System.out.println("id: " + user.getId());
            System.out.println("username: " + user.getUsername());
            System.out.println("password: " + user.getPassword());
            System.out.println("birthday: " + user.getBirthday());
        }
    }

    @Test
    public void selectMap() {
        List<Map<String, Object>> map =
                new Executor(MYSQL).selectMap(Map.class, "select username from user");
        for (Map<String, Object> user : map) {
            System.out.println("id: " + user.get("id"));
            System.out.println("username: " + user.get("username"));
            System.out.println("password: " + user.get("password"));
            System.out.println("birthday: " + user.get("birthday"));
        }
    }

    @Test
    public void selectOne() {
        User user = new Executor(MYSQL).selectOne(User.class, "select * from user where id = ?", 2);
        System.out.println("id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("birthday: " + user.getBirthday());
    }

    @Test
    public void selectMapOne() {
        Map<String, Object> user = new Executor(MYSQL)
                .selectMapOne(Map.class, "select password from user where id = ?", 2);
        System.out.println("id: " + user.get("id"));
        System.out.println("username: " + user.get("username"));
        System.out.println("password: " + user.get("password"));
        System.out.println("birthday: " + user.get("birthday"));
    }
}
