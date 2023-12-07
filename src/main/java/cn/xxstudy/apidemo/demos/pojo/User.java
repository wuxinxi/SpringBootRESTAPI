package cn.xxstudy.apidemo.demos.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @date: 2023/11/30 15:55
 * @author: TangRen
 * @remark:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Email(message = "邮箱不合法")
    @NotBlank(message = "邮箱不能为空")
    private String userEmail;

    @NotBlank(message = "密码不能为空")
    private String password;
}
