package com.wtgroup.demo.hbase.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.RegionSplitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * hbase管理者, 面向唯一的一个集群
 *
 * @author Nisus Liu
 * @version 0.0.1
 * @email liuhejun108@163.com
 * @date 2018/5/22 19:42
 */
public class HBaseManager {
    //TODO 暂设集群的唯一标识
    private final String clusterId = "defaultClusterId";

    public static final String HBASE_ZOOKEEPER_QUORUM = "offlinenode3,offlinenode4,offlinenode5";
    public static final int HBASE_TABLE_REGION_NUM = 10;
    public static final String COMMA = ",";
    public static final String HIVE_SEP = "\\x01";
    public static final String DEFAULT_COLUMNFAMILY = "defaultColumnFamily";
    public static final int SCAN_CACHING = 10000;                   //这只scan的缓存大小, 建议和记录条数同一数量级

    private static Logger LOG = LoggerFactory.getLogger(HBaseUtilSingleton.class);
    private static Configuration conf = null;
    private static Connection conn = null;
    private static ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private static Lock wlock = rwlock.writeLock();


    //存放本集群中的有可能要用到的列, 方便增删改的参数设置
    public Set<HColumn> columns = new LinkedHashSet<>();


    public HBaseManager addColumns(HColumn hColumn) {
        columns.add(hColumn);

        return this;
    }

    /**
     * 传入一个pojo类, 根据getter/setter获取对应的字段, 再转换为hbase中的字段名风格(通常i.e. camel style->underline style)
     * Note: 仅是为了方便于, `一个列族->一个pojo` 的场景.
     * @param pojo
     * @return
     */
    public HBaseManager addColumns(Class pojo, HColumn.ColumnFamily columnFamily) {
        Set<String> fieldNames = FieldNameUtil.getFieldNames(pojo);
        for (String f : fieldNames) {
            //驼峰名->下划线风格, 加入 columns 中
            HColumn hColumn = new HColumn(FieldNameUtil.underlineStyle(f));
            columns.add(hColumn);
        }

        return this;
    }



    public static Connection getConnection() {
        if (conn == null || conn.isClosed()) {
            try {
                wlock.lock();
                if (conn == null || conn.isClosed()) {
                    conf = getHBaseConfig();
                    conn = ConnectionFactory.createConnection(conf);
                    LOG.info("HBase connection is created:::");
                }
            } catch (IOException e) {
                LOG.error("HBase create conn error...\n{}", e);
            } finally {
                wlock.unlock();
            }


        }
        return conn;
    }

    public static Configuration getHBaseConfig() {
        if (conf == null) {
            try {
                wlock.lock();
                if (conf == null) {
                    conf = HBaseConfiguration.create();
                    //conf.set("hbase.zookeeper.quorum", ResourceUtil.getString("hbase.zookeeper.quorum"));
                    conf.set("hbase.zookeeper.quorum", HBASE_ZOOKEEPER_QUORUM);
                    conf.set("hbase.client.keyvalue.maxsize", "0");

                }
            } catch (Exception e) {
                LOG.error("Getting config error:", e);
            } finally {
                wlock.unlock();
            }
        }
        return conf;
    }

    public static boolean closeConnection() {
        if (conn != null && !conn.isClosed()) {
            try {
                conn.close();
                LOG.info("hbase connection close success:::");
                return true;
            } catch (IOException e) {
                LOG.error("hbase connection close error:::", e);
                return false;
            }
        }
        return true;
    }

    /**
     * synchronized can only solve concurrency at thread level, in distributed system multiple processes
     * unavoidably will face concurrent operation, to avoid multiple processed concurrency you have to
     * implement distributed lock via zookeeper e.g.
     *
     * @param tableName
     * @param family
     */
    synchronized public static void createTable(String tableName, String family) {
        try {
            Admin admin = getConnection().getAdmin();
            if (!admin.tableExists(TableName.valueOf(tableName))) {
                HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
                HColumnDescriptor columnDescriptor = new HColumnDescriptor(family);
                columnDescriptor.setCompressionType(Compression.Algorithm.SNAPPY);      ////设置压缩算法
                tableDescriptor.addFamily(columnDescriptor);

                //分区  ?没有找到jar包?  若要创建表, 这个很重要
                byte[][] splits = new RegionSplitter.HexStringSplit().split(HBASE_TABLE_REGION_NUM);

//                admin.createTable(tableDescriptor, splits);
                admin.createTable(tableDescriptor);
                LOG.info("创建表 " + tableName + " 成功!");
            }
        } catch (Exception e) {
            LOG.error("Creating table {} error ", tableName, e);
//            throw new GlobalCustomException(GlobalConf.ErrorType.UNKNOW_ERROR, e);
            //throw new GlobalCustomException(GlobalConf.ErrorType.UNKNOW_ERROR, e);
        }
    }

    /**
     * 获取指定 table 的 columnFamily 下的 若干列
     *
     * @param tableName
     * @param rowKey
     * @param family
     * @param fields    多个字段用 ','分隔
     * @return
     */
    public static String getFieldsVal(String tableName, String rowKey, String family,
                                      String fields) {
        long start = System.currentTimeMillis();
        Connection conn = getConnection();
        StringBuilder sb = new StringBuilder();
        Table table = null;
        if (StringUtils.isBlank(fields)) {
            return "";
        }
        try {
            String[] fs = fields.split(COMMA);

            table = conn.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));

            for (String f : fs) {
                System.out.println("将要查询字段: " + f);
                get.addColumn(Bytes.toBytes(family), Bytes.toBytes(f));
            }

            Result rst = table.get(get);

            for (int i = 0; i < fs.length; i++) {
                String f = fs[i];
                String col_val = Bytes.toString(rst.getValue(Bytes.toBytes(family), Bytes.toBytes(f)));
                LOG.debug("结果: " + f + "->" + col_val);
                //将个字段值拼接成字符串
                sb.append(col_val);
                if (i < fs.length - 1) {
                    sb.append(COMMA);
                }
            }
            ;
            long end = System.currentTimeMillis();
            LOG.debug("查询耗时: {}ms", end - start);


            LOG.debug("拼接后的字段值: {}, 分隔符: {}", sb.toString(), COMMA);
            return sb.toString();

        } catch (Exception e) {
            LOG.error(e.toString());
//            return Constant.NULL;
            return null;
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    LOG.error("Closing table {} error ", tableName, e);
//                    throw new GlobalCustomException(GlobalConf.ErrorType.UNKNOW_ERROR, e);
                }
            }
        }


    }

    /**
     * 对指定表下列族的若干列插入或更新
     *
     * @param tableName
     * @param rowKey
     * @param family
     * @param data
     */
    public static void insertOrUpdate(String tableName, String rowKey, String family,
                                      Map<String, String> data) {

        long startTime = System.currentTimeMillis();

        Connection conn = getConnection();
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            for (String key : data.keySet()) {
                String value = String.valueOf(data.get(key));
                if (value == null) {
                    continue;
                } else {
                    put.addColumn(Bytes.toBytes(family), Bytes.toBytes(key), Bytes.toBytes(value));
                }
            }
            if (put.size() > 0) {
                table.put(put);
                LOG.debug("Table {} insert or update with rowkey {} succeeded", tableName, rowKey);
            }
        } catch (TableNotFoundException | RetriesExhaustedWithDetailsException e) {
            // If table not exists, create it
            LOG.warn("Table {} not found, it will be created soon.", tableName);
            createTable(tableName, family);
            // run the method again
            insertOrUpdate(tableName, rowKey, family, data);
        } catch (Exception e) {
            LOG.error("Insert or update data error, table: {} ,pk :{}", tableName, rowKey);
            LOG.error("Insert or update data error, error:", e);
//            throw new GlobalCustomException(GlobalConf.ErrorType.UNKNOW_ERROR, e);
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    LOG.error("Closing table {} error ", tableName, e);
//                    throw new GlobalCustomException(GlobalConf.ErrorType.UNKNOW_ERROR, e);
                }
            }

            long endTime = System.currentTimeMillis();
            LOG.info("hbase success.spend time(mm):" + (endTime - startTime));
        }

    }

    /**
     * @param tableName  表名
     * @param rowKeyName rowkey在Map中的Key名称, 将作为hbase中rowKey
     * @param batchNo    批量提交的批次号：可以用transNo
     * @param family     列族名
     * @param datas      列表
     */
    public static void insertOrUpdateBatch(String tableName, String rowKeyName, String batchNo, String family,
                                           List<Map<String, String>> datas) {
        long startTime = System.currentTimeMillis();
        Connection conn = getConnection();
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            List<Put> list = new ArrayList<>();
            for (Map<String, String> data : datas) {
                String rk = data.get(rowKeyName);       //指定数据集中某一键的值作为 rowKey
                Put put = new Put(Bytes.toBytes(rk));
                for (String key : data.keySet()) {
//                    if (!key.equals(rowKeyName)) {
                    if (!rowKeyName.equals(key)) {      //map的key可以是null, 避免空指针异常 && 排除指定为rowKey的字段(避免重复存储)
                        String value = data.get(key);
                        if (StringUtils.isNotBlank(value)) {        //?""要不要存?
                            put.addColumn(Bytes.toBytes(family), Bytes.toBytes(key), Bytes.toBytes(value));
                        }
                    }
                }
                list.add(put);
            }

            if (list.size() > 0) {
                table.put(list);
                LOG.info("Table {},batch={} insert or update with num :{}  succeeded", new String[]{tableName, batchNo, datas.size() + ""});
            }
        } catch (TableNotFoundException | RetriesExhaustedWithDetailsException e) {
            // If table not exists, create it
            LOG.warn("Table {} not found, it will be created soon.", tableName);
            createTable(tableName, family);
            // run the method again
            insertOrUpdateBatch(tableName, rowKeyName, batchNo, family, datas);
        } catch (Exception e) {
            LOG.error("Insert or update data error, table: {} ,pk :{}", tableName, batchNo);
            LOG.error("Insert or update data error, error:", e);
//            throw new GlobalCustomException(GlobalConf.ErrorType.UNKNOW_ERROR, e);
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (IOException e) {
                    LOG.error("Closing table {} error ", tableName, e);
//                    throw new GlobalCustomException(GlobalConf.ErrorType.UNKNOW_ERROR, e);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        LOG.info("hbase batch success.spend time(mm):" + (endTime - startTime));
    }


    public static void deleteRowByRowkey(String tableName, String rowKey) {
        Connection conn = getConnection();
        Table table = null;
        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Delete del = new Delete(Bytes.toBytes(rowKey));
            table.delete(del);
            LOG.debug("Table {} delete with rowkey {} succeeded", tableName, rowKey);

        } catch (TableNotFoundException | RetriesExhaustedWithDetailsException e) {
            // If table not exists, create it
            LOG.warn("Table {} not found, it will be created soon.", tableName);
            createTable(tableName, DEFAULT_COLUMNFAMILY);
            // run the method again
            deleteRowByRowkey(tableName, rowKey);
        } catch (Exception e) {
            LOG.error("Deleting data error, table: {} ,pk :{}", tableName, rowKey);
            LOG.error("error:", e.getMessage());
//            throw new GlobalCustomException(GlobalConf.ErrorType.UNKNOW_ERROR, e);
        } finally {
            if (table != null) {
                try {
                    table.close();
                } catch (Exception e) {
                    LOG.error("Closing table {} error ", tableName, e);
//                    throw new GlobalCustomException(GlobalConf.ErrorType.UNKNOW_ERROR, e);
                }
            }
        }

    }


    /**
     * 给定rowKey前缀, 模糊匹配.
     * Note: 限定了family范围
     *
     * @param tableName
     * @param rowKeyPrefix
     * @param familyAndColumnPairs ["family01,column01","family02,column02",...]
     * @return
     */
    public static List<Result> getRowsByPrefix(String tableName,
                                               String rowKeyPrefix, Set<HColumn> columns,String startRow, String stopRow) {
        Connection conn = getConnection();
        Table table = null;
        List<Result> list = new ArrayList<>();
        ResultScanner scanner = null;

        try {
            long start = System.currentTimeMillis();
            table = conn.getTable(TableName.valueOf(tableName));
            PrefixFilter filter = new PrefixFilter(rowKeyPrefix.getBytes());
            Scan scan = new Scan();
            //设置cache
            scan.setCaching(SCAN_CACHING);
            scan.setFilter(filter);
            scan.setStartRow(startRow.getBytes());
            scan.setStopRow(stopRow.getBytes());


            for (HColumn column : columns) {
                LOG.debug("待查询列族: {}, 待查询列名: {}", column.getColumnFamily().getName(), column.getName());
                scan.addColumn(Bytes.toBytes(column.getColumnFamily().getName()), Bytes.toBytes(column.getName()));       //family, column

            }




            LOG.debug("即将开始scan查询");
            long startScan = System.currentTimeMillis();
            scanner = table.getScanner(scan);       //这里很快 匹配rowKey
            long endScan = System.currentTimeMillis();
            LOG.debug("获取ResultScanner耗时 : {}ms", endScan - startScan);


            long it0 = System.currentTimeMillis();

            //结果集处理

            Iterator<Result> results = scanner.iterator();

            while (results.hasNext()) {
                Result r = results.next();

                for (HColumn column : columns) {

                }



//                String rowId = Bytes.toString(r.getRow());
//
//                for (String fc : familyAndColumnPairs) {
//                    StringBuilder sb = new StringBuilder();
//                    String[] fcArr = fc.split(",");
//                    Cell cell = r.getColumnLatestCell(fcArr[0].getBytes(), fcArr[1].getBytes());
//                    String cellVal = new String(CellUtil.cloneValue(cell));
//                    sb.append("行名: ").append(rowId).append(", 列族名: ").append(fcArr[0]).append(", 列名: ").append(fcArr[1]).append(", 值: ").append(cellVal);
//                    //LOG.debug(res);
//                    list.add(sb.toString());
//                }

                list.add(r);
//
            };
            long it1 = System.currentTimeMillis();
            LOG.debug("迭代取Result耗时 : {}ms", it1-it0);


            ;
            long end = System.currentTimeMillis();
            LOG.info("前缀查询总耗时 : {}ms", end - start);


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


    /**
     * 获取所有数据
     * 指定表下多有family的所有字段, 所有行
     *
     * @param tableName
     * @throws Exception
     */
    public static void getAllRows(String tableName) throws Exception {
        Connection conn = getConnection();
        ResultScanner results = null;
        Table table = null;

        try {
            table = conn.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            results = table.getScanner(scan);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // 输出结果 TODO
        for (Result result : results) {
            for (KeyValue rowKV : result.raw()) {
                System.out.print("行名:" + new String(rowKV.getRow()) + " ");
                System.out.print("时间戳:" + rowKV.getTimestamp() + " ");
                System.out.print("列族名:" + new String(rowKV.getFamily()) + " ");
                System.out
                        .print("列名:" + new String(rowKV.getQualifier()) + " ");
                System.out.println("值:" + new String(rowKV.getValue()));
            }
        }
    }





    /**
     * @author Nisus Liu
     * @version 0.0.1
     * @email liuhejun108@163.com
     * @date 2018/5/22 19:28
     */
    public static class HColumn {

        //列名
        private String name;

        //列值  用于存储hbase中获取的结果
        private String value;

        //所属列族
        private ColumnFamily columnFamily;

        public HColumn() {
        }

        public HColumn(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public HColumn setName(String name) {
            this.name = name;
            return this;
        }

        public ColumnFamily getColumnFamily() {
            return columnFamily;
        }

        public HColumn setColumnFamily(ColumnFamily columnFamily) {
            this.columnFamily = columnFamily;
            return this;
        }

        public String getValue() {
            return value;
        }

        public HColumn setValue(String value) {
            this.value = value;
            return this;
        }

        class ColumnFamily {
            private String name;            //列族名
            private String table;           //表名

            public String getName() {
                return name;
            }

            public ColumnFamily setName(String name) {
                this.name = name;
                return this;
            }

            public String getTable() {
                return table;
            }

            public ColumnFamily setTable(String table) {
                this.table = table;
                return this;
            }
        }


    }
}
