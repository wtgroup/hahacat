package wtgroup.pojo;

import java.io.Serializable;
import java.util.*;

/**
 * 省市县类型的实体.
 * 辅助封装画图数据.
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-11-17:34
 */
public class PCDEntity {
    private Serializable id;         //区域的ID, 必须具有唯一性
    private String name;       //区域的名称
    /**
     * 数据源获取的原始数值,逐级累加的结果,且为double型.
     * 在null数据源排除的前台下, 基础数据可以设默认值0, 方便操作.
     */
    private Number value=0.0;      //区域的数值
    /**
     * 当使用聚合计算后, 结果数据存储在此字段
     */
    private Number statValue ; //统计数值,如均值,方差,中位数...

    private Boolean root=null;   //是否根节点
    private Boolean leaf=null;   //是否叶子节点
    private PCDEntity parent;
    //    private Map<Serializable,PCDEntity> children = new HashMap<Serializable,PCDEntity>(); //下级数据集合
    //换成set方便操作
    private Set<PCDEntity> children = new HashSet<PCDEntity>();

    public PCDEntity(Serializable id) {
        if (id == null) {
            throw new IllegalArgumentException("id不能为null");
        }
        this.id = id;
    }

    public Set<PCDEntity> getChildren() {
        return children;
    }

    public void setChildren(Set<PCDEntity> children) {
        this.children = children;
    }

    /**
     * 根据id在子集中找对象
     * @param childId
     * @return
     */
    public PCDEntity getChildById(Serializable childId) {
        for (PCDEntity child : children) {
            if (child.getId().equals(childId)) {
                return child;
            }
        }
        return null;
    }


    public Boolean isRoot() {
        if (parent != null) {
            return true;
        }
        return false;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

    public Boolean isLeaf() {
        if (children == null || children.isEmpty()) {
            return true;
        }
        return false;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Number getStatValue() {
        return statValue;
    }

    public void setStatValue(Number statValue) {
        this.statValue = statValue;
    }

    public PCDEntity getParent() {
        return parent;
    }

    public void setParent(PCDEntity parent) {
        this.parent = parent;
    }





    public Serializable getId() {
        return id;
    }

    public void setId(Serializable id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getValue() {
        return value;
    }


    public void setValue(Number value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "PCDEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", statValue=" + statValue +
                ", root=" + root +
                ", leaf=" + leaf +
                ", parent[id,name]=[" + (parent==null?"null,null":parent.id+","+parent.name) +
                "], children=" + children +
                '}';
    }

    /**
     * @param simple true:不递归打印子集的信息.
     * @return
     */
    public String toString(boolean simple) {
        String childrenString="";
        if (!simple) {
            toString();
        } else {
            childrenString = "children.size" + children.size();
        }
        return "PCDEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", statValue=" + statValue +
                ", root=" + root +
                ", leaf=" + leaf +
                ", parent[id,name]=[" + (parent==null?"null,null":parent.id+","+parent.name) +
                "],"+ childrenString +
                '}';
    }
}
