package wtgroup;

import org.apache.commons.logging.Log;
import wtgroup.aggregators.Aggregator;
import wtgroup.pojo.PCDEntity;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 形如province,city,district(简称PCD)这种层级关系的数据处理器.
 * <br/>
 * <br/>
 * 要求的数据格式:
 * <table>
 * <tr>
 * <th>省</th>
 * <th>市</th>
 * <th>区</th>
 * <th>数值</th>
 * </tr>
 * <tr>
 * <td>安徽省</td>
 * <td>合肥市</td>
 * <td>包河区</td>
 * <td>100</td>
 * </tr>
 * <tr>
 * <td>安徽省</td>
 * <td>合肥市</td>
 * <td>蜀山区</td>
 * <td>30</td>
 * </tr>
 * </table>
 * <br/>
 * <br/>
 * 之所以要求这种数据格式, 是因为这样可以达到一次访问数据库加载数据的目的.
 * 如果数据量过大,则也完全可以分组查询加载.因此这种设计机制可以让使用者超级灵活的实现自己的需求.<br/>
 * 首先经过处理得到如下格式, 这是将最低级别逐级分组<b>累加</b>得到的, 所以使用者需要注意
 * 根据自己的需求, 选择适当的方式从数据源获取数据.
 * <table>
 * <tr>
 * <th>省</th>
 * <th>省数值</th>
 * <th>市</th>
 * <th>市数值</th>
 * <th>区</th>
 * <th>区数值</th>
 * </tr>
 * <tr>
 * <td>安徽省</td>
 * <td>260</td>
 * <td>合肥市</td>
 * <td>130</td>
 * <td>包河区</td>
 * <td>100</td>
 * </tr>
 * <tr>
 * <td>安徽省</td>
 * <td>260</td>
 * <td>合肥市</td>
 * <td>130</td>
 * <td>蜀山区</td>
 * <td>30</td>
 * </tr>
 * </table>
 * <br/>
 * <br/>
 * 接下可通过指定聚合方式, 对数据进一步处理.<br/>
 * 这里内置了一些聚合计算方式:
 * <ul>
 * <li>
 * sum-默认,不用进一步计算,因为第一步对数据源的处理就已经得到了这种聚合结果.这种聚合方式是最常用,
 * 以此作为基础数据集是比较合理, 关键是方便灵活的进一步处理.
 * <br/>
 * 比如想要count聚合, 则只需要查询数据源时获取最低级别的计数,<code>buildPCDMap</code>
 * 即可得到需要的结果, 不需要多余的后期计算操作.<br/>
 * <li/>
 * <li>
 * count
 * </li>
 * <li>
 * mean
 * </li>
 * <li>
 * max
 * </li>
 * <li>
 * min
 * </li>
 * <li></li>
 * <ul/>
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-11-22:07
 */
public class PCDDataHelper {
    private PCDEntity rootEntity;
    private static Log logger = LogFactory.getLog(PCDDataHelper.class);
    private Aggregator aggregator;

    public void setAggregator(Aggregator aggregator) {
        this.aggregator = aggregator;
    }

    public PCDDataHelper() {
    }

    public PCDDataHelper(Aggregator aggregator) {
        this.aggregator = aggregator;
    }

    public static boolean isNullOrEmptyMap(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 将数据源获取到的数据集, 解析成PCDEntity实体.
     * @param dataframe         数据集, 注意格式规范.
     * @param idColIdxes        指定作为ID的列索引(自0开始,下同). 自左向右, 级别自高向低.
     * @param nameColIdxes      指定作为名称的列索引.
     * @param lowestValueColIdx 最低级别的数值对应的列索引.
     * @return
     */
    public PCDEntity buildPCDEntity(List<Object[]> dataframe, int[] idColIdxes, int[] nameColIdxes, int lowestValueColIdx) {
        if (idColIdxes.length != nameColIdxes.length) {
            throw new IllegalArgumentException("length of" + idColIdxes + "must be equals to" + nameColIdxes);
        }

        //根(虚拟的)
        PCDEntity root = new PCDEntity("root");
        root.setName("root");     //可以设置名称,刚好匹配highchart的字段
        root.setRoot(true);

        for (Object[] row : dataframe) {
            //基础数值不能为空
            if (row[lowestValueColIdx] == null) {
                continue;
            }
            row2PCDEntity(idColIdxes, nameColIdxes, lowestValueColIdx, 0, root, row);
        }


        this.rootEntity = root;
        return root;
    }


    /**
     * 数组(记录行)转成pcdentity.
     *
     * @param idColIdxes        各级别id对应的数据集列索引(0起始).
     * @param nameColIdxes      各级别name对应的数据集列索引(0起始).
     * @param lowestValueColIdx 最低级别的基础数值对应的列索引.
     * @param level             当前所处的级别.
     * @param pcdEntity
     * @param row               记录行.
     * @return
     */
    private double row2PCDEntity(int[] idColIdxes, int[] nameColIdxes, int lowestValueColIdx, int level, PCDEntity pcdEntity, Object[] row) {

        PCDEntity childPcdEntity = null;
        boolean isNewPcdEntity = false;

        //==未达到最低一级时==//
        Set<PCDEntity> children = pcdEntity.getChildren();
        //当前实体集合中获取当前ID对应的实体,将下面封装的下级对象存入
        childPcdEntity = pcdEntity.getChildById((Serializable) row[idColIdxes[level]]);
        if (childPcdEntity == null) {
            //表示当前遇到一个新pcdentity
            isNewPcdEntity = true;
            childPcdEntity = new PCDEntity((Serializable) row[idColIdxes[level]]);
            //关联父级
            childPcdEntity.setParent(pcdEntity);
            children.add(childPcdEntity);  //接下来封装的下级数据将会放入这个新子pcdentity中

            String pcdEntiyName = String.valueOf(row[nameColIdxes[level]]);
            childPcdEntity.setName(pcdEntiyName);
        }


        //==当到达最低一级时==//
        if (level >= idColIdxes.length - 1) { //当本次级别达到总级数目时,表示当前处于最低级别
            //[[
            logger.debug("到达最低层");
            if (!isNewPcdEntity) {
                System.out.println("警告: 最低级别记录[" + childPcdEntity.getId() + "," + childPcdEntity.getName() + "]存在重复记录,旧数值将被覆盖!");
            }
            Number pcdEntiyValue = 0.0;
            try {
                pcdEntiyValue = (Number) row[lowestValueColIdx];
            } catch (Exception e) {
                //数据源有可能为null(之前已经控制),那就是怕类型转换异常(基本不会)
            }


            double oldVal = 0.0;  //若是旧pcdentity,则保留下旧的值;新的,则存储的是默认值0
            try {
                oldVal = childPcdEntity.getValue().doubleValue();
            } catch (Exception e) {
            }
            //万一碰到重复的最低级别,则采用覆盖原则
            childPcdEntity.setValue(pcdEntiyValue.doubleValue());
            //非最低一级累加数值
            double accumValue = childPcdEntity.getValue().doubleValue() - oldVal;

            //更新当前节点(叶子的父节点)数值
            pcdEntity.setValue(pcdEntity.getValue().doubleValue() + accumValue);

            //返回所有父级数值应该累加的值
            return accumValue;
            // ]]
        }

        //[[
        // 向下递归封装数据id,name,children
        //调用自身
        double accumValue = row2PCDEntity(idColIdxes, nameColIdxes, lowestValueColIdx, level + 1, childPcdEntity, row);
        // ]]

        //[[
        //更新数值

        pcdEntity.setValue(pcdEntity.getValue().doubleValue() + accumValue);
                /*排除之前增加的数值,在加新获取的值. 如果是新的dist,则减去的默认值0*/
        // ]]

        return accumValue;
    }
/*
注意: 聚合计算时,一定要自顶向下,上级不能不能受下级污染.
如自定义的计算占比方式,如果先算了下级,*/


    /**
     * 统计聚合数据. 数值存储在statValue中.
     * 未使用聚合计算器时, 请在value字段中取值.
     *
     * @param pcdEntity
     */
    public PCDEntity statPCDEntity(PCDEntity pcdEntity) {
        //允许没有聚合计算器,没有则返回自身,就是默认的基本数据,也就是sum的效果
        if (aggregator == null) {
            return pcdEntity;
        }

        //叶子节点则返回
        if (pcdEntity.isLeaf()) {
            //出口
            return pcdEntity;
        }

        Set<PCDEntity> children = pcdEntity.getChildren();

        for (PCDEntity child : children) {
            //递归调用
            statPCDEntity(child);

        }//end for

        //调用聚合计算器计算
        PCDEntity[] group = new PCDEntity[children.size()];
        int i = 0;
        for (PCDEntity e : children) {
            //剔除基础值为空的pcdentity,不参与聚合计算(buildPCDEntity中已经做过了)
            group[i++] = e;
        }

        aggregator.aggregate(group);


        return pcdEntity;


    }


}




/*
//省集合中获取当前ID对应的省对象,将下面封装的下级对象存入
            PCDEntity prov = provinces.get(row[level1Id]);
            if (prov == null) {
                //表示当前遇到一个新省
                prov = new PCDEntity();
                provinces.put((Serializable) row[level1Id], prov);  //接下来封装的下级数据将会放入这个新省中

                prov.setId((Serializable) row[level1Id]);
                String provName = String.valueOf(row[level1Name]);
                prov.setName(provName);
            }

            //==解析并封装市级数据[中间级别]==//

            //拿出当前省包含的市集合
            Map<Serializable, PCDEntity> provChildren = prov.getChildren();
            //根据当前市ID拿出市,用来封装市级数据
            PCDEntity city = provChildren.get(row[level2Id]);
            if (city == null) {
                //表示遇到一个新市
                city = new PCDEntity();
                provChildren.put((Serializable) row[level2Id], city);
                city.setId((Serializable) row[level2Id]);
                city.setName(String.valueOf(row[level2Name]));
            }

            //==区级数据[最低一级]==//

            //拿出当前市包含的区集合
            Map<Serializable, PCDEntity> cityChildren = city.getChildren();
            //根据当前市ID拿出区,用来封装区级数据
            PCDEntity dist = cityChildren.get(row[lowestLevelId]);
            Number distValue = (Number) row[lowestLevelValue];
            if (dist == null) {
                dist = new PCDEntity();
                //存入至当前市的区集合
                cityChildren.put((Serializable) row[lowestLevelId], dist);
                dist.setId((Serializable) row[lowestLevelId]);
                dist.setName(String.valueOf(row[lowestLevelName]));


            } else {
                System.out.println("警告: [" + dist.getId() + dist.getName() + "]存在记录,相应数值仅保留最近一次的!");

            }

            double oldV = dist.getValue();  //旧dist,则存储旧的值;新dist,则存储的是默认值0
            //万一碰到重复的最低级别,则采用覆盖原则
            dist.setValue((distValue.doubleValue()));
            //非最低一级累加数值
            double accumValue = dist.getValue() - oldV;

            //==递归更新父级数值字段==//
            city.setValue(city.getValue() + accumValue);
                //排除之前增加的数值,在加新获取的值. 如果是新的dist,则减去的默认值0
            prov.setValue(prov.getValue() + accumValue);
*/
