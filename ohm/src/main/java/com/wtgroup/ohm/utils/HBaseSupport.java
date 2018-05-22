package com.wtgroup.ohm.utils;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.PrefixFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-23-1:29
 */
public class HBaseSupport<T> {
    private HBaseManager manager;

    public HBaseSupport(HBaseManager manager) {
        this.manager = manager;
    }

    public List<String> getRowsByPrefix(String rowKeyPrefix, T hEntity) {
        Connection conn = manager.getConnection();
        Table table = null;
        List<String> list = new ArrayList<String>();
        ResultScanner scanner = null;
        try {
            long start = System.currentTimeMillis();
            table = conn.getTable(TableName.valueOf(tableName));
            PrefixFilter filter = new PrefixFilter(rowKeyPrefix.getBytes());
            Scan scan = new Scan();
            scan.setFilter(filter);
            for (String v : familyAndColumnPairs) {
                String[] s = v.split(",");
                LOG.debug("待查询列族: {}, 待查询列名: {}",s[0],s[1]);
                scan.addColumn(Bytes.toBytes(s[0]), Bytes.toBytes(s[1]));       //family, column
            }

            LOG.debug("即将开始scan查询");
            scanner = table.getScanner(scan);
//            for (Result result : scanner) {
//                list.add(result);           //TODO 返回结果友好化
//            }

            //结果打印
            Iterator<Result> results = scanner.iterator();
            while (results.hasNext()) {
                Result r = results.next();
                String rowId = Bytes.toString(r.getRow());

                for (String fc : familyAndColumnPairs) {
                    String[] fcArr = fc.split(",");
                    Cell cell = r.getColumnLatestCell(fcArr[0].getBytes(), fcArr[1].getBytes());
                    String cellVal = new String(CellUtil.cloneValue(cell));
                    String res = "行名: " + rowId + ", 列族名: " + fcArr[0] + ", 列名: " + fcArr[1] + ", 值: " + cellVal;
                    LOG.debug(res);
                    list.add(res);
                }

            };
            long end = System.currentTimeMillis();
            LOG.info("前缀查询耗时 : {}ms", end-start);



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return list;
    }



}
