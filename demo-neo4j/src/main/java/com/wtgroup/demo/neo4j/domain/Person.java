package com.wtgroup.demo.neo4j.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.parboiled.support.Var;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 观众
 *
 * @author Nisus-Liu
 * @version 1.0.0
 * @email liuhejun108@163.com
 * @date 2018-04-20-14:00
 */
@NodeEntity
public class Person {
    @GraphId
    private Long id;
    private String name;
    private int sex;
    @DateLong       //图数据库 long 值存储
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create;

    @Relationship(type="朋友",direction=Relationship.OUTGOING)    //边 边的类型, 边的方向(缺省为OUTGOING)
    @JsonIgnore
    private Set<Person> friends = new HashSet<>();
    @Relationship(type="评分")
    private Set<Rating> ratings = new HashSet<>();
    @Relationship(type="观看",direction = Relationship.OUTGOING)
    private Set<Show> watchs = new HashSet<>();

    //提供添加关系的方法
    public void addFriend(Person person){
        friends.add(person);
    }
    public void addRating(Rating rating ){
        ratings.add(rating);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Set<Person> getFriends() {
        return friends;
    }

    public void setFriends(Set<Person> friends) {
        this.friends = friends;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<Show> getWatchs() {
        return watchs;
    }

    public void setWatchs(Set<Show> watchs) {
        this.watchs = watchs;
    }
}
