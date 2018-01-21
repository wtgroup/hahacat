package com.wtgroup.utils.pcddata.aggregators;

import com.wtgroup.utils.pcddata.pojo.PCDEntity;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-13-14:36
 */ //计数聚合计算器
public class CountAggregator implements Aggregator {


    /**
     * 对当前pcdentity的子集(set集合)进行聚合计算.
     * sum,count,max,min,mean...这类聚合结果,上级依赖下级数值,需要获取下级数值,来更新自己的值,称为"纵向依赖型聚合".
     * 纵向依赖型聚合时, 将本级组聚合结果向上级"报告".
     * 占比...每个成员只用需要关系自己和同组成员之间的数值关系. 不用考虑上下级数值,称为"横向依赖型聚合". 聚合结果不能上报.
     * 类似mean这样的统计数值!较为特殊, 这样自下向上聚合, 会导致均值距离一般普通的均值有点偏差.
     * 上级依赖了下级的均值,一般应该是依赖下级的基本数值. 利用PCDEntity字段statValue解决.
     *
     * @param group 当期pcdentity的子集
     * @return
     */

    public void aggregate(PCDEntity[] group) {
        if (group == null || group.length == 0) {
            return;
        }
        for (PCDEntity member : group) {
            if (!member.isLeaf()) {
                //每个组员的数值就是其子集成员的总数目
                member.setStatValue(member.getChildren().size());
            } else {
                //没有子集时,数值就是1
                member.setStatValue(1);
            }
        }
    }
}
