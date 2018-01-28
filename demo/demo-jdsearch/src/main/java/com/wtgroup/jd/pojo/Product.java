package com.wtgroup.jd.pojo;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-23-20:16
 */
public class Product {

    private String pid;
    private String name;

    private String catalog_name;

    private float price;

    private String description;

    private String picture;

    public Product() {
    }

    public Product(String pid, String name, String catalog_name, float price, String description, String picture) {
        this.pid = pid;
        this.name = name;
        this.catalog_name = catalog_name;
        this.price = price;
        this.description = description;
        this.picture = picture;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog_name() {
        return catalog_name;
    }

    public void setCatalog_name(String catalog_name) {
        this.catalog_name = catalog_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
