package com.wtgroup.blog.entity;

public class TComment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.id
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.nick_name
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private String nickName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.email
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.site
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private String site;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.parent_id
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.comment_obj
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private Long commentObj;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.status
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.create_time
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private Long createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.audit_user
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private Long auditUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.audit_time
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private Long auditTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.content
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.id
     *
     * @return the value of t_comment.id
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.id
     *
     * @param id the value for t_comment.id
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.nick_name
     *
     * @return the value of t_comment.nick_name
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.nick_name
     *
     * @param nickName the value for t_comment.nick_name
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.email
     *
     * @return the value of t_comment.email
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.email
     *
     * @param email the value for t_comment.email
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.site
     *
     * @return the value of t_comment.site
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public String getSite() {
        return site;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.site
     *
     * @param site the value for t_comment.site
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setSite(String site) {
        this.site = site == null ? null : site.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.parent_id
     *
     * @return the value of t_comment.parent_id
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.parent_id
     *
     * @param parentId the value for t_comment.parent_id
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.comment_obj
     *
     * @return the value of t_comment.comment_obj
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public Long getCommentObj() {
        return commentObj;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.comment_obj
     *
     * @param commentObj the value for t_comment.comment_obj
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setCommentObj(Long commentObj) {
        this.commentObj = commentObj;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.status
     *
     * @return the value of t_comment.status
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.status
     *
     * @param status the value for t_comment.status
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.create_time
     *
     * @return the value of t_comment.create_time
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.create_time
     *
     * @param createTime the value for t_comment.create_time
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.audit_user
     *
     * @return the value of t_comment.audit_user
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public Long getAuditUser() {
        return auditUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.audit_user
     *
     * @param auditUser the value for t_comment.audit_user
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setAuditUser(Long auditUser) {
        this.auditUser = auditUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.audit_time
     *
     * @return the value of t_comment.audit_time
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public Long getAuditTime() {
        return auditTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.audit_time
     *
     * @param auditTime the value for t_comment.audit_time
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setAuditTime(Long auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.content
     *
     * @return the value of t_comment.content
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.content
     *
     * @param content the value for t_comment.content
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}