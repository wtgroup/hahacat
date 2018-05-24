package com.wtgroup.ohm.utils;

import com.wtgroup.ohm.annotation.support.HEntityDescriptor;
import com.wtgroup.ohm.annotation.support.ResultHandler;
import com.wtgroup.ohm.annotation.support.SimpleResultHandler;
import com.wtgroup.ohm.bean.Column;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-23-1:29
 */
public abstract class HBaseSupport<T> {
    private final Logger log = LoggerFactory.getLogger(HBaseSupport.class);
    private HBaseManager manager = new HBaseManager();
    private T hEntity;
    private HEntityDescriptor<T> hEntityDescriptor;


    public HBaseSupport() {
        //子类继承时, 获取泛型的实例
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType prtype = (ParameterizedType) type;
            Type[] args = prtype.getActualTypeArguments();
            Class<T> hEntityClzz = (Class<T>) args[0];
            try {
                hEntity = hEntityClzz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //根据传入的实体类获取对应的描述信息
            hEntityDescriptor = new HEntityDescriptor<T>(hEntityClzz);

            //解析注解, 得到表名,row key,列族,列



            //结果集封装利用反射

            //生成指定的实体类的代理对象, 包含更多的信息


            //.instance();


        }
    }


    //
//
    public List<T> getRowsByPrefix(String rowKeyPrefix) {
        Connection conn = manager.getConnection();
        Table table = null;
        List<String> list = new ArrayList<String>();
        ResultScanner scanner = null;
        try {
            long start = System.currentTimeMillis();
            table = conn.getTable(TableName.valueOf(hEntityDescriptor.getTable()));
            PrefixFilter filter = new PrefixFilter(rowKeyPrefix.getBytes());
            Scan scan = new Scan();
            scan.setFilter(filter);

            Set<Column> columns = hEntityDescriptor.getColumns();
            for (Column col : columns) {
                log.debug("待查询列族: {}, 待查询列名: {}", col.getFamily(), col.getName());
                scan.addColumn(Bytes.toBytes(col.getFamily()), Bytes.toBytes(col.getName()));       //family, column
            }


            log.debug("即将开始scan查询");
            scanner = table.getScanner(scan);
//            for (Result result : scanner) {
//                list.add(result);           //TODO 返回结果友好化
//            }

            //结果疯转
            Iterator<Result> results = scanner.iterator();
            while (results.hasNext()) {
                Result r = results.next();

                //调用结果处理器处理
                ResultHandler<T> handler = new ResultHandler<>() {
                    @Override
                    public Object handle(Result result, Object hEntity,) {



                        return null;
                    }
                };


                ResultHandler resultHandler = new SimpleResultHandler();

                String rowKey = Bytes.toString(r.getRow());

                //
                for (Column col : columns) {

                }




                SimpleResultMapper<T> mapper = new SimpleResultMapper<>();

                mapper.mapping()



//                for (String fc : familyAndColumnPairs) {
//                    String[] fcArr = fc.split(",");
//                    Cell cell = r.getColumnLatestCell(fcArr[0].getBytes(), fcArr[1].getBytes());
//                    String cellVal = new String(CellUtil.cloneValue(cell));
//                    String res = "行名: " + rowId + ", 列族名: " + fcArr[0] + ", 列名: " + fcArr[1] + ", 值: " + cellVal;
//                    LOG.debug(res);
//                    list.add(res);
                }

            } catch (IOException e1) {
            e1.printStackTrace();
        }
        ;
            long end = System.currentTimeMillis();
            log.info("前缀查询耗时 : {}ms", end - start);


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

    protected abstract T resultHandler(Result r);


}
