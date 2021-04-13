import com.komodo.community.utils.YamlUtil;
import com.komodo.community.yaml.OriginTrackedYamlLoader;
import com.komodo.jdbc.executor.Executor;
import com.komodo.community.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

import static com.komodo.community.enums.DatasourceEnum.MYSQL;

/**
 * @Author ZhangGJ
 * @Date 2021/04/05 11:01
 */
@Slf4j
public class JdbcTest {

    @Test
    public void insert() {
        new Executor(MYSQL)
                .insert("insert into user (username, password, birthday) values(?,?,?)", "铁头娃",
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
        new Executor(MYSQL).delete("delete from user where id in (?, ?, ?)", 89, 90, 91);
    }

    @Test
    public void update() {
        new Executor(MYSQL).update("update user set username = ? where id = ?", "愣头青", 88);
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
        User user =
                new Executor(MYSQL).selectOne(User.class, "select * from user where id = ?", 88);
        System.out.println("id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("birthday: " + user.getBirthday());
    }

    @Test
    public void selectMapOne() {
        Map<String, Object> user = new Executor(MYSQL)
                .selectMapOne(Map.class, "select password from user where id = ?", 88);
        System.out.println("id: " + user.get("id"));
        System.out.println("username: " + user.get("username"));
        System.out.println("password: " + user.get("password"));
        System.out.println("birthday: " + user.get("birthday"));
    }

    @Test
    public void testSpringYaml() {
        Map<String, Object> oracle = YamlUtil.readYaml("config-database.yml", "oracle", Map.class);
        List<Map<String, Object>> load = new OriginTrackedYamlLoader().load("config-database.yml");
        System.out.println(oracle);
    }

    @Test
    public void testYaml() {
        Yaml yaml = new Yaml();
        Iterable<Object> iterable = yaml.loadAll(
                YamlUtil.class.getClassLoader().getResourceAsStream("config-database.yml"));
        while (iterable.iterator().hasNext()) {
            Object next = iterable.iterator().next();
            System.out.println();
        }
    }
}
