package com.wtgroup.utils.pcddata.x03换行符切割;

import com.wtgroup.utils.pcddata.constant.Profile;
import org.junit.Test;

/**
 * @version:
 * @author: Nisus-Liu
 * @email: liuhejunlj@163.com
 * @date: 2018-01-05-12:18
 */
public class Tst01 {


    @Test
    public void fun01() {
        String sep = Profile.NEW_LINE;
        String s = sep+sep+"you are" + sep + "hello hhh" + sep + sep + "沃尔玛的" + sep+sep;
        String[] split = s.split(sep);
        System.out.println(split);

    }

}
