package com.wtgroup.ohm.annotation.support;

import com.wtgroup.ohm.bean.Column;
import com.wtgroup.ohm.bean.HEntityDescriptor;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Set;

/**
 * 结果封装处理器
 *
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/5/23 15:57
 */
public class SimpleResultMapper<T> implements ResultMapper<T> {

    public SimpleResultMapper() {}

    @Override
    public T mapping(Result result, HEntityDescriptor<T> descriptor) throws IllegalAccessException {
        Set<Column> columns = descriptor.getColumns();
        T hEntity = descriptor.gethEntity();
        descriptor.getRowKey().getField().set(hEntity, Bytes.toString(result.getRow()));
        for (Column column : columns) {
            Cell cell = result.getColumnLatestCell(column.getFamily().getBytes(), column.getName().getBytes());
            byte[] cellVal = CellUtil.cloneValue(cell);
            column.getField().setAccessible(true);
            //这里为了简单, 统一转为string类型  TODO 不同类型分别处理
            column.getField().set(hEntity,Bytes.toString(cellVal));
        }

        return hEntity;
    }
}



