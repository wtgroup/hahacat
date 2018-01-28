package com.wtgroup;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-22-19:18
 */
public class Zi extends Fu{

    private int id;
    private String name="Zi";

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String testGet() {
        System.out.println(super.getName());
        System.out.println(getName());
        System.out.println(this.getName());
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
