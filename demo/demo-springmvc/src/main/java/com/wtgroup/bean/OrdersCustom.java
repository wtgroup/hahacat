package com.wtgroup.bean;

/**
 * orders的扩展bean
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-17-16:57
 */
public class OrdersCustom extends Orders {

    //等待扩展属性
    /**
     * 图片路径(相对).
     */
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
