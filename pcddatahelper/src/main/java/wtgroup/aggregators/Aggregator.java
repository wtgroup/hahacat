package wtgroup.aggregators;

import wtgroup.pojo.PCDEntity;

/**
 * 聚合计算器.
 *
 * 实现这个接口, 自定义聚合计算方式.
 */
public interface Aggregator {

    /**
     * 这里让你选择如何处理每一组的数值.对当前分组(pcdentity集合)进行聚合计算. .
     * sum,count,max,min...这类聚合结果需要向上级报告,称为"报告型聚合", 需要将聚合的值设置给父级pcdentity.
     * mean,占比...这类组内计算好后,分配给组内成员即可,不能上报,称为"内聚型聚合", 不能将值设置给父级pcdentity
     *
     * @param group
     * @return 返回父级pcdentity
     */
    public void aggregate(PCDEntity[] group);


}
