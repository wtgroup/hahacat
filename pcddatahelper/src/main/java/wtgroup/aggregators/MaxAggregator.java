package wtgroup.aggregators;

import wtgroup.pojo.PCDEntity;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-13-14:36
 */ //最大值聚合计算器
public class MaxAggregator implements Aggregator {
    private boolean isMaxAggregator = true;

    /**
     * 对当前pcdentity的子集(set集合)进行聚合计算.
     *
     * @param group 当期pcdentity的子集
     * @return
     */
    //sum,count,max,min,mean...这类聚合结果,上级依赖下级数值,需要获取下级数值,来更新自己的值,称为"纵向依赖型聚合".
    //占比...每个成员只用需要关系自己和同组成员之间的数值关系. 不用考虑上下级数值,称为"横向依赖型聚合".
    //!类似mean这样的统计数值!较为特殊, 这样自下向上聚合, 会导致均值距离一般普通的均值优点偏差.上级依赖了下级的均值,一般应该是依赖下级的基本数值. 利用PCDEntity字段statValue解决.
    public void aggregate(PCDEntity[] group) {

        if (group == null || group.length == 0) {
            return;
        }
        //遍历找出最大值
        double maxmin = group[0].isLeaf() ? group[0].getValue().doubleValue() : group[0].getStatValue().doubleValue();
        for (PCDEntity member : group) {
            double v = member.isLeaf() ? member.getValue().doubleValue() : member.getStatValue().doubleValue();
            //求最大值时用
            if (isMaxAggregator && v > maxmin) {
                maxmin = v;
            }
            //求最小值时用
            if (!isMaxAggregator && v < maxmin) {
                maxmin = v;
            }
        }
        //向上报告
        group[0].getParent().setStatValue(maxmin);
    }


}
