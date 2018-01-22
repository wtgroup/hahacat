package x01_创建索引库;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 索引维护: 增删改查
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-21-22:29
 */
public class Demo02 {

    private IndexWriter indexWriter;
    private IndexSearcher indexSearcher;


    /**
     * 删除所有
     */
    @Test
    public void fun01() throws IOException {
        indexWriter.deleteAll();
        indexWriter.close();
    }


    /**
     * 查询   查询所有
     */
    @Test
    public void fun02() throws IOException {
        Query query = new MatchAllDocsQuery();
        TopDocs topDocs = indexSearcher.search(query, 5);
        printSearchResult(topDocs);
        indexSearcher.getIndexReader().close();
    }

    //查询    精准查询

    /**
     * 查询   范围查询
     */
    @Test
    public void fun03() throws IOException {
        //(1)域名(2)最小值(3)最大值(4)(5)边界控制, 是否包含最小/最大值自身
        Query query = NumericRangeQuery.newLongRange("fileSize", 490L, 1000L, true, true);
        TopDocs topDocs = indexSearcher.search(query, 5);
        printSearchResult(topDocs);

        indexSearcher.getIndexReader().close();

    }


    /**
     * 输出结果
     */
    public void printSearchResult(TopDocs topDocs) throws IOException {
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            //获取document的ID
            int docId = scoreDoc.doc;
            //根据ID搜索document
            Document document = indexSearcher.doc(docId);

            //取出文档内的field
            System.out.println(document.get("fileName"));
            System.out.println(document.get("fileSize"));
            System.out.println(document.get("filePath"));
            System.out.println(document.get("fileContent"));
            System.out.println("-------------------------------------");
        }

    }

    @Before
    /**
     * 抽取方法获取IndexWriter
     */
    public void createIndexWriter( ) throws IOException {
        // 1. 创建索引输出流:indexWriter
        // 1.1 指定索引库位置
        FSDirectory directory = FSDirectory.open(new File("D:\\DevelopKit\\Lucene\\index"));
        // 1.2 指定分析器, 用以对文档内容进行分析
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        indexWriter = new IndexWriter(directory, indexWriterConfig);
    }

    /**
     * 抽取: 获取IndexReader
     */
    @Before
    public void crateIndexReader( ) throws IOException {
        // 1. 创建indexReader, 需要为其指定索引库位置
        FSDirectory directory = FSDirectory.open(new File("D:\\DevelopKit\\Lucene\\index"));
        IndexReader indexReader = DirectoryReader.open(directory);
        // 2. 创建indexSearcher对象
        this.indexSearcher = new IndexSearcher(indexReader);

    }


}
