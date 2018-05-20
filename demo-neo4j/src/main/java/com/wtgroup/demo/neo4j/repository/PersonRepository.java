package com.wtgroup.demo.neo4j.repository;

import com.wtgroup.demo.neo4j.domain.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * sdn接口, 实现crud
 */
@Repository("personRepository")
public interface PersonRepository extends GraphRepository<Person>{
    //内置方法

    //按照命名规则自定义声明方法
    public Iterable<Person> findByNameLike(@Param("name") String name); //非RESTful下 @param可省略

    //加@Query注解(不受命名规则约束)
    @Query("MATCH (n:Person) where ID(n) <> {id} return n limit 10;")
    public Iterable<Person> diyQueryByIdIsNot(@Param("id") int id);

    /**统计姓名以 xxx 开头的节点个数
     * @param regEx
     * @return
     */
    //MATCH (n:Person) where n.name=~'Lilly.*' RETURN count(n)
    @Query("MATCH (n:Person) where n.name=~{name} RETURN count(n)")
    public Long countWhereNameLike(@Param("name") String regEx);

}
