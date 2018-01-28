package com.wtgroup.blog.controller.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-27-14:06
 */
@Getter
@Setter
@ToString
public class TestVo {
    @Min(value = 6,message="id must great than 6")
    @Max(value = 12,message = "id must less than 12")
    private String id;

    @NotBlank(message = "name 不能为空!")
    private String name;

    //密码和确认密码校验
    //密码不为空  密码和确认密码一致
    @NotBlank(message = "'密码'不能为空")
    @Length(min=6,max=16,message = "'密码'长度必须位于6~16")
    private String password;

    @NotBlank(message = "'确认密码'不能为空")
    @Length(min=6,max=16,message = "'确认密码'长度必须位于6~16")
    private String confirmPassword;

    //判断密码和确认密码是否一致的方法
    @AssertTrue(message = "'确认密码'和'密码'必须一致!")
    public boolean isEqualsPassword(){
        return confirmPassword != null && confirmPassword.equals(password);
    }



}
