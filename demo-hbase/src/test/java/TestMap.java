import org.junit.Test;

import java.util.HashMap;

/**
 * @author Nisus Liu
 * @version 0.0.0
 * @email liuhejun108@163.com
 * @date 2018/5/17 14:09
 */
public class TestMap {



    @Test
    public void fun01(){
        HashMap<String, String> map = new HashMap<>();
        map.put(null,"这是空");
        System.out.println(map.get(null));
    }
}
