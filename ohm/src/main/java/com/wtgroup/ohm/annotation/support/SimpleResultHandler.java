package com.wtgroup.ohm.annotation.support;

import com.wtgroup.ohm.bean.Column;
import com.wtgroup.ohm.utils.SimpleResultMapper;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * 结果封装处理器
 *
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/5/23 15:57
 */
public class SimpleResultHandler implements ResultHandler{

    private Result result;
    private Object hEntity;


    public SimpleResultHandler() {
        this.result = result;
        this.hEntity = hEntity;


    }


    public Object handle() {
        //获取对象的columns
        Field columnsF = null;
        Set<Column> columns = null;
        try {
            columnsF = hEntity.getClass().getDeclaredField("columns");
            columnsF.setAccessible(true);
            columns = (Set<Column>) columnsF.get(hEntity);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        if (columns != null) {
            for (Column col : columns) {
                Cell cell = result.getColumnLatestCell(col.getFamily().getBytes(), col.getName().getBytes());
                    String cellVal = new String(CellUtil.cloneValue(cell));

                    hEntity.getClass().getDeclaredField(col.)


            }
        }

        String[] fcArr = fc.split(",");
//                    Cell cell = r.getColumnLatestCell(fcArr[0].getBytes(), fcArr[1].getBytes());
//                    String cellVal = new String(CellUtil.cloneValue(cell));
//                    String res = "行名: " + rowId + ", 列族名: " + fcArr[0] + ", 列名: " + fcArr[1] + ", 值: " + cellVal;
//                    LOG.debug(res);
//                    list.add(res);
    }


    @Override
    public Object handle(Result result, HEntityDescriptor descriptor) {
        Set<Column> columns = descriptor.getColumns();
        for (Column column : columns) {
                    Cell cell = result.getColumnLatestCell(column.getFamily().getBytes(), column.getName().getBytes());
                    //这里为了简单, 统一转为string类型  TODO 不同类型分别处理
            new SimpleResultMapper<>().mapping(CellUtil.cloneValue(cell), column.getName(), column.getFamily(), descriptor.getTable());

//                    String res = "行名: " + rowId + ", 列族名: " + fcArr[0] + ", 列名: " + fcArr[1] + ", 值: " + cellVal;
//                    LOG.debug(res);
//                    list.add(res);
        }


        return null;
    }
}


}
