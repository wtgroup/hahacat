package com.wtgroup.blog.entity;

public class TArticleWithBLOBs extends TArticle {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_article.content
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_article.html
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private String html;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_article.tags
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    private String tags;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_article.content
     *
     * @return the value of t_article.content
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_article.content
     *
     * @param content the value for t_article.content
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_article.html
     *
     * @return the value of t_article.html
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public String getHtml() {
        return html;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_article.html
     *
     * @param html the value for t_article.html
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setHtml(String html) {
        this.html = html == null ? null : html.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_article.tags
     *
     * @return the value of t_article.tags
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public String getTags() {
        return tags;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_article.tags
     *
     * @param tags the value for t_article.tags
     *
     * @mbggenerated Fri Jan 26 01:48:50 CST 2018
     */
    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }
}