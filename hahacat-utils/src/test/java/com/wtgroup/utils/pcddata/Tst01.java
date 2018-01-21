package com.wtgroup.utils.pcddata;

import com.wtgroup.utils.pcddata.x01测试Jsoup插件.TstBean;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2017-12-22-14:34
 */
public class Tst01 {


    @Test
    public void fun01() {
        ArrayList<TstBean> list= new ArrayList<TstBean>();
        TstBean tstBean = new TstBean();
        tstBean.setName("hhhh");
        list.add(tstBean);
        list.get(0).setName("xiangni");

        System.out.println(list.get(0).getName());
    }

}
