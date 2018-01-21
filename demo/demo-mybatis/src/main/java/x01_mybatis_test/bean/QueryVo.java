package x01_mybatis_test.bean;

import java.io.Serializable;
import java.util.List;

/**
 * bean包装类
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-15-10:24
 */
public class QueryVo implements Serializable{
    private User user;
    private Orders orders;
    private List<Integer> idlist;
    private Integer[] idarray;

    public List<Integer> getIdlist() {
        return idlist;
    }

    public void setIdlist(List<Integer> idlist) {
        this.idlist = idlist;
    }

    public Integer[] getIdarray() {
        return idarray;
    }

    public void setIdarray(Integer[] idarray) {
        this.idarray = idarray;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
