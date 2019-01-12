package com.wtgroup.demo.demospringboot.bean;

import org.springframework.beans.factory.annotation.Autowired;

/**用于手动注册
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/12/30 20:22
 */
public class ManualBean {

    // 手动注册时, 依赖属性会自动注入
    @Autowired
    private AutoBean autoBean;

    public void print() {
        System.out.println("ManualBean::");
        autoBean.print();
    }

}
