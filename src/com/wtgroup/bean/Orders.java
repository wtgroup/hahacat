package com.wtgroup.bean;

import java.util.Date;

public class Orders {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.id
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.user_id
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.number
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.createtime
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orders.note
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    private String note;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.id
     *
     * @return the value of orders.id
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.id
     *
     * @param id the value for orders.id
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.user_id
     *
     * @return the value of orders.user_id
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.user_id
     *
     * @param userId the value for orders.user_id
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.number
     *
     * @return the value of orders.number
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public String getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.number
     *
     * @param number the value for orders.number
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.createtime
     *
     * @return the value of orders.createtime
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.createtime
     *
     * @param createtime the value for orders.createtime
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orders.note
     *
     * @return the value of orders.note
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orders.note
     *
     * @param note the value for orders.note
     *
     * @mbggenerated Wed Jan 17 21:45:56 CST 2018
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}