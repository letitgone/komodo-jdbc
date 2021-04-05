import com.komodo.jdbc.configuration.Executor;
import com.komodo.jdbc.pojo.DatasourceEnum;
import org.junit.Test;
import pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @Author ZhangGJ
 * @Date 2021/04/05 11:01
 */
public class JdbcTest {

    @Test
    public void insert() {
        Executor.build(DatasourceEnum.MYSQL);
        Executor.insert("insert into user (username, password, birthday) values(?,?,?)", "Tom",
                "123456", "2021-02-09");
    }

    @Test
    public void update() {
        Executor.build(DatasourceEnum.MYSQL);
        Executor.update("update user set username = ? where id = ?", "铁头娃", 1);
    }

    @Test
    public void delete() {
        Executor.build(DatasourceEnum.MYSQL);
        Executor.delete("delete from user where id not in (?, ?)", 1, 2);
    }

    @Test
    public void selectList() {
        Executor.build(DatasourceEnum.MYSQL);
        List<User> userList = Executor.selectList(User.class, "select * from user");
        for (User user : userList) {
            System.out.println("id: " + user.getId());
            System.out.println("username: " + user.getUsername());
            System.out.println("password: " + user.getPassword());
            System.out.println("birthday: " + user.getBirthday());
        }
    }

    @Test
    public void selectMap() {
        Executor.build(DatasourceEnum.MYSQL);
        List<Map<String, Object>> map = Executor.selectMap(Map.class, "select username from user");
        for (Map<String, Object> user : map) {
            System.out.println("id: " + user.get("id"));
            System.out.println("username: " + user.get("username"));
            System.out.println("password: " + user.get("password"));
            System.out.println("birthday: " + user.get("birthday"));
        }
    }

    @Test
    public void selectOne() {
        Executor.build(DatasourceEnum.MYSQL);
        User user = Executor.selectOne(User.class, "select * from user where id = ?", 2);
        System.out.println("id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("birthday: " + user.getBirthday());
    }

    @Test
    public void selectMapOne() {
        Executor.build(DatasourceEnum.MYSQL);
        Map<String, Object> user =
                Executor.selectMapOne(Map.class, "select password from user where id = ?", 2);
        System.out.println("id: " + user.get("id"));
        System.out.println("username: " + user.get("username"));
        System.out.println("password: " + user.get("password"));
        System.out.println("birthday: " + user.get("birthday"));
    }
}
