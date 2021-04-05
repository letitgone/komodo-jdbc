package pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ZhangGJ
 * @Date 2021/04/05 21:33
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 5614107405074727748L;

    private Long id;

    private String username;

    private String password;

    private String birthday;
}
