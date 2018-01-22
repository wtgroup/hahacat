package x01_创建索引库;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 原生Lucene创建索引流程.
 * 查询索引.
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-01-21-19:25
 */
public class Demo01 {

    /**
     * 创建索引
     * @throws IOException
     */
    @Test
    public void fun01() throws IOException {
        // 1. 创建索引输出流:indexWriter
        // 1.1 指定索引库位置
        FSDirectory directory = FSDirectory.open(new File("D:\\DevelopKit\\Lucene\\index"));
        // 1.2 指定分析器, 用以对文档内容进行分析
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        // 2. 创建Document对象  (放入for循环体内)

        // 3. 创建field对象, 将field对象添加至document中  遍历当前路径下的索引文本文件
        // 3.1 指定数据源
        File src = new File("D:\\DevelopKit\\Lucene\\测试文本");
        // 3.3 遍历取文件, 建立field
        if (src.isDirectory()) {
            File[] files = src.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                Document document = new Document();
                //域1: 文件名
                String fileName = file.getName();
                Field fileNameField = new TextField("fileName", fileName, Field.Store.YES);
                //域2: 文件大小
                long size = FileUtils.sizeOf(file);
                Field fileSizeField = new LongField("fileSize", size, Field.Store.YES);
                //域3: 文件路径
                String filePath = file.getPath();
                Field filePathField = new StoredField("filePath", filePath);//强制store
                //域4: 文件内容
                String fileContent = FileUtils.readFileToString(file, "GBK");
                Field fileContentField = new TextField("fileContent ", fileContent, Field.Store.YES);

                //3.4 将以上域添加到document中
                document.add(fileNameField);
                document.add(fileSizeField);
                document.add(filePathField);
                document.add(fileContentField);

                // 4. 利用indexWriter将document写入索引库  测过程进行索引创建, 并将索引和document写入索引库
                indexWriter.addDocument(document);
            }

        }

        // 5. 关闭indexWriter
        indexWriter.close();
    }


    /**
     * 查询索引
     * @throws IOException
     */
    @Test
    public void fun02() throws IOException {
        // 1. 创建indexReader, 需要为其指定索引库位置
        FSDirectory directory = FSDirectory.open(new File("D:\\DevelopKit\\Lucene\\index"));
        IndexReader indexReader = DirectoryReader.open(directory);
        // 2. 创建indexSearcher对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 3. 指定termQuery  根据这个来搜索
//        TermQuery termQuery = new TermQuery(new Term("fileName", "hello"));
        TermQuery termQuery = new TermQuery(new Term("fileContent", "public"));

        // 4. 搜索
        TopDocs topDocs = indexSearcher.search(termQuery, 3);   //这里面包含ScoreDoc[], 即ScoreDoc包含document的ID
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

        indexReader.close();
    }




}
