package com.wtgroup.ohm.utils;

/**框架内部会给出每个Result的表名, 列族, 列名, 值(二级制).
 * 由使用者定制结果映射代码.
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-05-24-0:08
 */
public interface ResultMapper<T> {

    public T mapping(byte[] columnVal, String columnName, String columnFamily, String table)



}
