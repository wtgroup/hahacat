package wtgroup.aggregators;

import wtgroup.pojo.PCDEntity;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-13-14:36
 */ //最小值聚合计算器
public class MinAggregator implements Aggregator {


    public void aggregate(PCDEntity[] group) {
        new MaxAggregator().aggregate(group);
    }
}
