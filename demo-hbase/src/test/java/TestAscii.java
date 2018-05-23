import org.junit.Test;
import sun.security.util.Length;

/**
 * @author Nisus Liu
 * @version 0.0.0
 * @email liuhejun108@163.com
 * @create 2018/5/21 18:30
 */
public class TestAscii {




    public String nextString(String value)
    {
        Integer ascii = 0;
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        char last = chars[chars.length - 1];
        chars[chars.length-1] = (char) ((int)last+1);
        for (char aChar : chars) {
            sbu.append(aChar);
        }

        return sbu.toString();
    }

    @Test
    public void fun01(){
        String s = "abcg-3526347458X-";


        System.out.println(nextString(s));


    }
}
