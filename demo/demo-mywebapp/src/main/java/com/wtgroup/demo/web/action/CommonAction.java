package com.wtgroup.demo.web.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2018-01-06-16:34
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class CommonAction<T> extends ActionSupport implements ModelDriven{

    private T model;

    public CommonAction() {
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType prtype = (ParameterizedType) type;
            Type[] args = prtype.getActualTypeArguments();
            Class<T> arg = (Class<T>) args[0];
            try {
                model = arg.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        this.model = model;
    }

    @Override
    public T getModel() {
        return model;
    }

}
