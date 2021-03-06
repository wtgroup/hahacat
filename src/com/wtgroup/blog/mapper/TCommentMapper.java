package com.wtgroup.blog.mapper;

import com.wtgroup.blog.entity.TComment;
import com.wtgroup.blog.entity.TCommentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCommentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int countByExample(TCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int deleteByExample(TCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int insert(TComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int insertSelective(TComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    List<TComment> selectByExampleWithBLOBs(TCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    List<TComment> selectByExample(TCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    TComment selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByExampleSelective(@Param("record") TComment record, @Param("example") TCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") TComment record, @Param("example") TCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByExample(@Param("record") TComment record, @Param("example") TCommentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByPrimaryKeySelective(TComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(TComment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_comment
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    int updateByPrimaryKey(TComment record);
}