package com.wtgroup.utils.pcddata.demo;

import com.wtgroup.utils.pcddata.PCDDataHelper;
import com.wtgroup.utils.pcddata.aggregators.MaxAggregator;
import com.wtgroup.utils.pcddata.pojo.PCDEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-13-13:24
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        List<Object[]> dataframe = loadPcdData();

        //==不使用任何聚合策略==//

        //创建助手
        PCDDataHelper helper = new PCDDataHelper();

        PCDEntity root = helper.buildPCDEntity(dataframe, new int[]{0, 2, 4}, new int[]{1, 3, 5}, 6);

        //自定义toString,不递归打印子集信息,当然默认是递归打印的
        System.out.println(root.toString(true));

        System.out.println("---------------------------");



        //==使用聚合计算器==//
        //最大值聚合计算器演示--实现了递归求出每组最大值
        PCDDataHelper helper1 = new PCDDataHelper(new MaxAggregator());
        //or:
        //helper.setAggregator(new MaxAggregator());
        //聚合器不影响解析数据源,还利用上文的root结果
        //不设置聚合器则默认就是sum的效果

        //调用方法统计数据
        PCDEntity rootAfterAggregate = helper1.statPCDEntity(root);
        System.out.println(rootAfterAggregate.toString());


    }

    private static List<Object[]> loadPcdData() throws IOException {
        //数据库查询出来的数据源通常是这样的list
        List<Object[]> dataframe = new ArrayList<Object[]>();
        //读取csv测试数据
        InputStream src = Demo.class.getClassLoader().getResourceAsStream("pcd_data.CSV");
        InputStreamReader r = new InputStreamReader(src);
        BufferedReader br = new BufferedReader(r);
        String line;
        String head = br.readLine();
        while ((line = br.readLine()) != null) {
            String[] split = line.split(",");
            Object[] row = new Object[split.length];
            int i = 0;
            for (String s : split) {
                s = s.trim();
                if (i == split.length - 1) {
                    row[i++] = Double.parseDouble(s);
                    break;
                }
                row[i++]=s;
            }
            dataframe.add(row);
        }
        return dataframe;
    }


}
