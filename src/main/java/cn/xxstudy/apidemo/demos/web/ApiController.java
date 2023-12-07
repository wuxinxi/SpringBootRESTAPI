package cn.xxstudy.apidemo.demos.web;

import cn.xxstudy.apidemo.demos.pojo.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2023/11/30 15:51
 * @author: TangRen
 * @remark:
 */
@RestController
@RequestMapping("/api")
@Validated
public class ApiController {

    @GetMapping("/list")
    public List<User> getUser() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("99@qq.com", "admin"));
        list.add(new User("88@qq.com", "admin2"));
        return list;
    }

    @PostMapping("/register")
    public User register(@RequestBody @Valid User user) {
        return user;
    }

    @GetMapping("/login")
    public User login(@NotBlank(message = "email不能为空") @Email(message = "email不合法") String email) {
        return new User(email, "m12345");
    }


}
