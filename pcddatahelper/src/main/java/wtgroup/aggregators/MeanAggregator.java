package wtgroup.aggregators;

import wtgroup.pojo.PCDEntity;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-13-14:36
 */ //均值聚合计算器
public class MeanAggregator implements Aggregator {


    /**
     * 对当前pcdentity的子集(set集合)进行聚合计算.
     *
     * @param group 当期pcdentity的子集
     * @return
     */
    //sum,count,max,min,mean...这类聚合结果,上级依赖下级数值,需要获取下级数值,来更新自己的值,称为"纵向依赖型聚合".
    //占比...每个成员只用需要关系自己和同组成员之间的数值关系. 不用考虑上下级数值,称为"横向依赖型聚合".
    //!类似mean这样的统计数值!较为特殊, 这样自下向上聚合, 会导致均值距离一般普通的均值有点偏差.
    // 上级依赖了下级的均值,一般应该是依赖下级的基本数值. 利用PCDEntity字段statValue解决.
    public void aggregate(PCDEntity[] group) {
        //不出意外,来的group已经处理好了,不会是null或empty
        if (group == null || group.length == 0) {
            return;
        }
        //上级基本值除以本级集合size即使均值
        double numer = 0.0;
        try {
            numer = group[0].getParent().getValue().doubleValue();
        } catch (Exception e) {
            //本机求和
            for (PCDEntity m : group) {
                numer += m.getValue().doubleValue();
            }
        }

        //向上报告
        group[0].getParent().setStatValue(numer / group.length);

    }


}
