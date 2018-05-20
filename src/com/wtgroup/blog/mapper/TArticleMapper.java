package com.wtgroup.blog.mapper;

import com.wtgroup.blog.entity.TArticle;
import com.wtgroup.blog.entity.TArticleExample;
import com.wtgroup.blog.entity.TArticleWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TArticleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int countByExample(TArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int deleteByExample(TArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int insert(TArticleWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int insertSelective(TArticleWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    List<TArticleWithBLOBs> selectByExampleWithBLOBs(TArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    List<TArticle> selectByExample(TArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    TArticleWithBLOBs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByExampleSelective(@Param("record") TArticleWithBLOBs record, @Param("example") TArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") TArticleWithBLOBs record, @Param("example") TArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByExample(@Param("record") TArticle record, @Param("example") TArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByPrimaryKeySelective(TArticleWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(TArticleWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_article
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByPrimaryKey(TArticle record);
}