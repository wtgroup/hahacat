import com.wtgroup.demo.hbase.Application;
import com.wtgroup.demo.hbase.config.PersonConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/6/10 19:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration
public class ConfTest {
    @Autowired
    private PersonConfig personConfig;
    @Test
    public void fun1() {
        System.out.println(personConfig);
    }


}
